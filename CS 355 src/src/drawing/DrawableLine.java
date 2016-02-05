package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Line;

public class DrawableLine extends Line implements DrawableShape {

	public DrawableLine(Color color, Double start, Double end) {
		super(color, start, end);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		g2g.drawLine(0, 0,(int) (this.getEnd().getX() - this.getCenter().getX()), 
				(int) (this.getEnd().getY() - this.getCenter().getY()));
		
	}

	@Override
	public void drawOutline(Graphics2D g2g) {
		// TODO Auto-generated method stub
		
	}

}
