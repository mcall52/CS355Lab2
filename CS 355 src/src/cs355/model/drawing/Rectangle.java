package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

/**
 * Add your rectangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Rectangle extends Shape {

	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Rectangle(Color color, Point2D.Double center, double width, double height) {

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.width = width;
		this.height = height;
	}

	/**
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	public Point2D.Double getUpperLeft() {
		Point2D.Double upperleft = 
				new Point2D.Double(getCenter().getX() - (getWidth() / 2), getCenter().getY() - (getHeight() / 2));
		return upperleft;
	}
	
//	private Point2D.Double getLowerRight() {
//		Point2D.Double lowerright = 
//				new Point2D.Double(getCenter().getX(), arg1)
//	}

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
		
		//if objpt is inside shape
		boolean isInside = false;
		if(objpt.getX() <= this.getWidth()/2 && objpt.getX() >= -this.getWidth()/2
				&& objpt.getY() <= this.getHeight()/2 && objpt.getY() >= -this.getHeight()/2){
			isInside = true;
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
		double ydif = objpt.getY() + (getHeight()/2 + HANDLE_DIST - HANDLE_RADIUS);
		
		if(Math.pow(xdif, 2) + Math.pow(ydif, 2) <= Math.pow(HANDLE_RADIUS, 2)){
			isInside = true;
		}
		return isInside;
	}

}
