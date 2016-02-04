package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Triangle;

public class DrawableTriangle extends Triangle implements DrawableShape {

	public DrawableTriangle(Color color, Double center, Double a, Double b, Double c) {
		super(color, center, a, b, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		int[] xpoints = new int[]{(int) this.getA().getX(), 
				(int) this.getB().getX(), (int) this.getC().getX()};
		int[] ypoints = new int[]{(int) this.getA().getY(), 
				(int) this.getB().getY(), (int) this.getC().getY()};
		g2g.fillPolygon(xpoints, ypoints, xpoints.length);
	}

}