package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * Add your triangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Triangle extends Shape {

	// The three points of the triangle.
	private Point2D.Double a;
	private Point2D.Double b;
	private Point2D.Double c;
	
	private Point2D.Double highestpoint;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param a the first point, relative to the center.
	 * @param b the second point, relative to the center.
	 * @param c the third point, relative to the center.
	 */
	public Triangle(Color color, Point2D.Double center, Point2D.Double a,
					Point2D.Double b, Point2D.Double c)
	{

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.a = a;
		this.b = b;
		this.c = c;
	}

	/**
	 * Getter for the first point.
	 * @return the first point as a Java point.
	 */
	public Point2D.Double getA() {
		return a;
	}

	/**
	 * Setter for the first point.
	 * @param a the new first point.
	 */
	public void setA(Point2D.Double a) {
		this.a = a;
	}

	/**
	 * Getter for the second point.
	 * @return the second point as a Java point.
	 */
	public Point2D.Double getB() {
		return b;
	}

	/**
	 * Setter for the second point.
	 * @param b the new second point.
	 */
	public void setB(Point2D.Double b) {
		this.b = b;
	}

	/**
	 * Getter for the third point.
	 * @return the third point as a Java point.
	 */
	public Point2D.Double getC() {
		return c;
	}

	/**
	 * Setter for the third point.
	 * @param c the new third point.
	 */
	public void setC(Point2D.Double c) {
		this.c = c;
	}

	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
		//throw new UnsupportedOperationException("Not supported yet.");
		Point2D.Double objpt = new Point2D.Double();
		AffineTransform worldToObj = new AffineTransform();
		worldToObj.rotate(-this.getRotation());
		worldToObj.translate(-this.getCenter().getX(), -this.getCenter().getY());
		worldToObj.transform(pt, objpt);
		
		boolean isInside = true;
		Point2D.Double[] points = new Point2D.Double[]{this.a, this.b, this.c};
		for(int i = 0; i < points.length; i++){
			int next = i + 1;
			if (next > 2){
				next = 0;
			}
			Point2D.Double distCornToPt = new Point2D.Double(objpt.getX() - points[i].getX(), 
					objpt.getY() - points[i].getY());
			Point2D.Double orth = new Point2D.Double((points[next].getX() - points[i].getX()), 
					points[next].getY() - points[i].getY());
			double dotProduct = distCornToPt.getX() * -orth.getY() +
					distCornToPt.getY() * orth.getX();
			if(dotProduct < 0){
				isInside = false;
			}
		}
		return isInside;
	}

	@Override
	public boolean pointInHandle(Double pt, double tolerance) {
		Point2D.Double objpt = new Point2D.Double();
		AffineTransform worldToObj = new AffineTransform();
		worldToObj.rotate(-this.getRotation());
		worldToObj.translate(-this.getCenter().getX(), -this.getCenter().getY());
		worldToObj.transform(pt, objpt);
		
		boolean isInside = false;
		double xdif = objpt.getX();
		double ydif = objpt.getY() + (getHighestPoint().getY() + HANDLE_DIST - HANDLE_RADIUS);
		
		if(Math.pow(xdif, 2) + Math.pow(ydif, 2) <= Math.pow(HANDLE_RADIUS, 2)){
			isInside = true;
		}
		return isInside;
	}
	
	protected Point2D.Double getHighestPoint(){
		Point2D.Double highestpoint = new Point2D.Double(0, 0);
		if(highestpoint.getY() < this.getA().getY()){
			highestpoint = this.getA();
		}
		if(highestpoint.getY() < this.getB().getY()){
			highestpoint = this.getB();
		}
		if(highestpoint.getY() < this.getC().getY()){
			highestpoint = this.getC();
		}
		
		return highestpoint;
	}

}
