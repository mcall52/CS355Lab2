package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your square code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Square extends Shape {

	// The size of this Square.
	private double size;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param size the size of the new shape.
	 */
	public Square(Color color, Point2D.Double center, double size) {

		// Initialize the superclass.
		super(color, center);

		// Set the field.
		this.size = size;
	}

	/**
	 * Getter for this Square's size.
	 * @return the size as a double.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Setter for this Square's size.
	 * @param size the new size.
	 */
	public void setSize(double size) {
		this.size = size;
	}
	
	public Point2D.Double getUpperLeft() {
		Point2D.Double upperleft = 
				new Point2D.Double(getCenter().getX() - (getSize() / 4), getCenter().getY() - (getSize() / 4));
		return upperleft;
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
		worldToObj.translate(-this.getCenter().getX(), -this.getCenter().getY());
		worldToObj.rotate(-this.getRotation());
		worldToObj.transform(pt, objpt);
		
		//if objpt is inside shape
		boolean isInside = false;
		if(objpt.getX() <= this.getSize()/4 && objpt.getX() >= -this.getSize()/4
				&& objpt.getY() <= this.getSize()/4 && objpt.getY() >= -this.getSize()/4){
			isInside = true;
		}
		return isInside;
	}

}
