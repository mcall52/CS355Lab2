package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Square;

public class DrawableSquare extends Square implements DrawableShape {

	public DrawableSquare(Color color, Double center, double size) {
		super(color, center, size);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		g2g.fillRect((int) -(this.getSize()/2)/2, -((int) this.getSize()/2)/2, 
				(int) this.getSize()/2, (int) this.getSize()/2);
	}

	@Override
	public void drawOutline(Graphics2D g2g) {
		g2g.setColor(Color.YELLOW);
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		g2g.drawRect((int) -(this.getSize()/2)/2, -((int) this.getSize()/2)/2, 
				(int) this.getSize()/2, (int) this.getSize()/2);
	}

	@Override
	public void dragShape(Graphics2D g2g, int index) {
		// TODO Auto-generated method stub
		
	}

}
