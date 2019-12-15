package Transform;

public class Matrix3D {
	
	private float matrix[];
	
	public Matrix3D(){
		matrix = new float[16];
		setIdentity();
	}
	
	public Matrix3D(float X, float Y, float Z){
		//translate
		this();
		setValue(0, 3, X);
		setValue(1, 3, Y);
		setValue(2, 3, Z);
		
	}
	
	public Matrix3D(double angle){
		//rotation

		matrix = new float[16];
		setIdentity();
		
		setValue(0, 0, (float) Math.cos(angle));
		setValue(0, 2, (float) Math.sin(angle));
		setValue(2, 0, (float) -Math.sin(angle));
		setValue(2, 2, (float) Math.cos(angle));
	}
	
//	public Matrix3D(float scalefactor){
//		//projection
//		
//	}
	
	public void setValue(int i, int j, float value){
		matrix[i * 4 + j] = value;
	}
	
	public float getValue(int i, int j){
		return matrix[i * 4 + j];
	}
	
	private void setIdentity(){
		for (int i=0; i<4; i++)
	      for (int j=0; j<4; j++)
	        matrix[i*4 + j] = (float) (i == j ? 1.0 : 0.0);
	}
	
	public void multiply(Matrix3D m2){
		Matrix3D product = new Matrix3D();
		
		for (int i = 0; i < 4; i++){
		   for (int j = 0; j < 4; j++){
		      for (int k = 0; k < 4; k++){
		         product.setValue(i, j, (product.getValue(i, j) + (this.getValue(i, k) * m2.getValue(k, j))));
		      }
		   }
		}
		matrix = product.matrix;
	}
	
	public float[] multiply(float[] m2){
		float[] product = new float[]{0, 0, 0, 0};
		
		for (int i = 0; i < 4; i++){
			for (int k = 0; k < 4; k++){
				product[i] += this.getValue(i, k) * m2[k];
			}
		}
		return product;
	}
}
