package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Circle;

public class DrawableCircle extends Circle implements DrawableShape {

	Point2D.Double upperleft;
	
	public DrawableCircle(Color color, Double center, double radius) {
		super(color, center, radius);
		// TODO Auto-generated constructor stub
		this.upperleft = new Point2D.Double(center.getX() - radius, center.getY() - radius);
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		g2g.fillOval((int) -(this.getRadius()), (int) -(this.getRadius()), 
				(int) this.getRadius()*2, (int) this.getRadius()*2);
	}

	@Override
	public void drawOutline(Graphics2D g2g) {
		// TODO Auto-generated method stub
		
	}

}
