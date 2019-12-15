package cs355.view;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Observable;

import Transform.Matrix3D;
import Transform.Transform;
import cs355.GUIFunctions;
import cs355.controller.DrawingImage;
import cs355.model.drawing.DrawingModel;
import cs355.model.drawing.Line;
import cs355.model.drawing.Shape;
import cs355.model.image.CS355Image;
import cs355.model.scene.CS355Scene;
import cs355.model.scene.Instance;
import cs355.model.scene.Line3D;
import cs355.model.scene.Point3D;
import drawing.DrawableShape;
import drawing.DrawableShapeFactory;

public class View implements ViewRefresher {

	private DrawableShapeFactory factory = new DrawableShapeFactory();
	private DrawingModel model;
	
	private double scalefactor;
	private Point2D.Double viewupperleft;
	
	private CS355Scene scene;
	private Point3D campos;
	private float camangle;
	
	public boolean is3d = false;
	
	private DrawingImage image;
	private boolean drawImage = false;
	
	public View(){
		scalefactor = 1.0;
		campos = new Point3D(0, 0, 0);
		camangle = 0;
		//doZoom();
	}

	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		
		for(Shape s: model.getShapes()){
			DrawableShape shape = factory.createShape(s);
			//create and set world to view transform
			AffineTransform worldToView = 
					Transform.worldToView(viewupperleft.x, viewupperleft.y, scalefactor);
			factory.setWorldToView(worldToView);
			shape.draw(g2d);
		}
		DrawableShape selectedShape = factory.createShape(model.getSelectedShape());
		if(selectedShape != null) {
			selectedShape.drawOutline(g2d);
		}
		
		if(is3d){
			g2d.setTransform(new AffineTransform());
			render(g2d);
		}
		
		if(drawImage){
			AffineTransform worldToView = //new AffineTransform();
					Transform.worldToView(viewupperleft.x, viewupperleft.y, scalefactor);
			
			g2d.drawImage(image.getImage(), worldToView, null);
		}
	}

	public void addModel(DrawingModel model) {
		this.model = model;
		model.addObserver(this);
		
	}

	public double getScaleFactor(){
		return scalefactor;
	}
	
	public void zoomIn(){
		scalefactor *= 2;
	//doZoom();
	}
	
	public void zoomOut(){
		scalefactor /= 2;
		//doZoom();
	}
	
	public void doZoom(){
		int vbarsize = (int) (512 / scalefactor);
		int hbarsize = (int) (512 / scalefactor);

		GUIFunctions.setZoomText(scalefactor);
		
		GUIFunctions.setHScrollBarKnob(hbarsize);
		GUIFunctions.setHScrollBarPosit(1024 - (hbarsize / 2));
		
		GUIFunctions.setVScrollBarKnob(vbarsize);
		GUIFunctions.setHScrollBarPosit(1024 - (vbarsize / 2));
	}
	
	public void setViewUpperLeft(Point2D.Double vul){
		viewupperleft = vul;
	}
	
	public void render(Graphics2D g2g){
		//do tranformations
		//draw house
		
		for(Instance inst : scene.instances()){
			Matrix3D worldToCam = Transform.worldToCamera(scene.getCameraPosition(), (float) scene.getCameraRotation());

			for(Line3D line : inst.getModel().getLines()){
				//transform
				float[] objToWorldStart = Transform.objToWorld3D(line.start, 
						(float) inst.getRotAngle(), inst.getPosition());
				float[] objToWorldEnd = Transform.objToWorld3D(line.end, 
						(float) inst.getRotAngle(), inst.getPosition());
				System.out.println("World: " + objToWorldStart[0] + " " + objToWorldStart[1]
						+ " " + objToWorldStart[2]);
				float[] transtartarr = worldToCam.multiply(objToWorldStart);
				float[] tranendarr = worldToCam.multiply(objToWorldEnd);
				
				System.out.println("Screen: " + transtartarr[0] + " " 
						+ transtartarr[1] + " " + transtartarr[2]);

				
//				Point3D transtartpoint = arrToPoint3D(transtartarr);
//				Point3D tranendpoint = arrToPoint3D(tranendarr);
//				
//				Point2D.Double start = Transform.screenTran(transtartpoint);
//				Point2D.Double end = Transform.screenTran(tranendpoint);
				
				//draw
				Matrix3D clip = Transform.getClip(1000, 1);
				float[] clippedStart = clip.multiply(transtartarr);
				float[] clippedEnd = clip.multiply(tranendarr);
				System.out.println("Clip: " + clippedStart[0] + " " + clippedStart[1] 
						+ " " + clippedStart[2]);
				
				//clip test
				if((!isclipped(clippedStart) || !isclipped(clippedEnd)) 
						&& !isTooClose(clippedStart) && !isTooClose(clippedEnd)){
					
				
					
					Point2D.Double start = Transform.screenTran(clippedStart);
					Point2D.Double end = Transform.screenTran(clippedEnd);
					
					
	//				Shape line3d = new Line(inst.getColor(), start, end);
					
					System.out.println("Line: " + (start) + " " + end);
	//				DrawableShape shape = factory.createShape(line3d);
					g2g.setColor(inst.getColor());
					g2g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);;
				}
			}
		}
	}
	
	public void setScene(CS355Scene scene){
		this.scene = scene;
	}
	
	private Point2D.Double to2d(Point3D three){
		return new Point2D.Double(three.x, three.y);
	}
	
	public void setCamPos(Point3D campos){
		this.campos = campos;
	}
	
	public void setCamangle(float camangle){
		this.camangle = camangle;
	}
	
	private Point3D arrToPoint3D(float[] arr){
		return new Point3D(arr[0], arr[1], arr[2]);
	}
	
	private boolean isclipped(float[] vector){
		boolean result = false;
		
		if(vector[0] < -vector[3]){
			result = true;
		}
		else if(vector[0] > vector[3]){
			result = true;
		}
		else if(vector[1] < -vector[3]){
			result = true;
		}
		else if(vector[1] > vector[3]){
			result = true;
		}
		
		return result;
	}
	
	private boolean isTooClose(float[] vector){
		if(vector[2] < -vector[3] || vector[2] > vector[3]){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setImage(CS355Image image2){
		this.image = (DrawingImage) image2;
	}
	
	public void toggleDrawImage(){
		drawImage = !drawImage;
		image.toggleDisplayImage();
	}
}
