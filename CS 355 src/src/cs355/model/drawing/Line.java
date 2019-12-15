package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import Transform.Transform;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape {

	// The ending point of the line.
	private Point2D.Double end;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param start the starting point.
	 * @param end the ending point.
	 */
	public Line(Color color, Point2D.Double start, Point2D.Double end) {

		// Initialize the superclass.
		super(color, start);

		// Set the field.
		this.end = end;
	}

	/**
	 * Getter for this Line's ending point.
	 * @return the ending point as a Java point.
	 */
	public Point2D.Double getEnd() {
		return end;
	}

	/**
	 * Setter for this Line's ending point.
	 * @param end the new ending point for the Line.
	 */
	public void setEnd(Point2D.Double end) {
		this.end = end;
	}
	
	/**
	 * Getter for this Line's starting point.
	 * @return the "center" which is the starting point
	 */
	public Point2D.Double getStart(){
		return getCenter();
	}
	
	protected double getLength(){
		double startx = 0;
		double starty = 0;
		double endx = getEnd().getX();
		double endy = getEnd().getY();
		
		return Math.sqrt(Math.pow((endx - startx), 2) + Math.pow(endy - starty, 2));
	}

	/**
	 * Add your code to do an intersection test
	 * here. You <i>will</i> need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
		Point2D.Double objpt = new Point2D.Double();
		AffineTransform worldToObj = 
				Transform.worldToObj(getCenter().getX(), getCenter().getY(), getRotation());
		//Point2D.Double objend = new Point2D.Double();
		//Point2D.Double objstart = new Point2D.Double();
//		worldToObj.rotate(-this.getRotation());
//		worldToObj.translate(-this.getCenter().getX(), -this.getCenter().getY());
		worldToObj.transform(pt, objpt); 
		//worldToObj.transform(this.getStart(), objstart);
		//worldToObj.transform(this.getEnd(), objend);
		//center at 0,0
		Point2D.Double objcenter = new Point2D.Double(0, 0);
		
		double startx = objcenter.getX();	//p0.x
		double starty = objcenter.getY();	//p0.y
		double endx = this.getEnd().getX();		//p1.x
		double endy = this.getEnd().getY();		//p1.y
		double length = getLength();		
		
		Point2D.Double dhat = new Point2D.Double((endx - startx)/length, (endy - starty)/length);
		
		double tx = (objpt.getX() - startx) * dhat.getX(); //(q - p0) * dhat
		double ty = (objpt.getY() - starty) * dhat.getY(); //(q - p0) * dhat
		double t = tx + ty;
		
		Point2D.Double q = new Point2D.Double(startx + t * dhat.getX(), starty + t * dhat.getY()); //q = p0 + t * dhat
		double distFromLine = Math.sqrt(Math.pow(q.getX() - objpt.getX(), 2) 
				+ Math.pow(q.getY() - objpt.getY(), 2)); 
		
		if(distFromLine <= tolerance){
			return true;
		}
		else {
			return false;
		}
//		if(objpt.getX() <= this.getWidth()/2 && objpt.getX() >= -this.getWidth()/2
//				&& objpt.getY() <= this.getHeight()/2 && objpt.getY() >= -this.getHeight()/2){
//			isInside = true;
//		}
	}

	@Override
	public boolean pointInHandle(Double pt, double tolerance) {
		Point2D.Double objpt = new Point2D.Double();
		AffineTransform worldToObj = 
				Transform.worldToObj(getCenter().getX(), getCenter().getY(), getRotation());
//		worldToObj.rotate(-this.getRotation());
//		worldToObj.translate(-this.getCenter().getX(), -this.getCenter().getY());
		worldToObj.transform(pt, objpt);
		
		boolean isInside = false;
		double xdif = objpt.getX();
		double ydif = objpt.getY();
		
		if(Math.pow(xdif, 2) + Math.pow(ydif, 2) <= Math.pow(HANDLE_RADIUS, 2)){
			isInside = true;
		}
		return isInside;
	}

}
