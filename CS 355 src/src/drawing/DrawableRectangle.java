package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Rectangle;

public class DrawableRectangle extends Rectangle implements DrawableShape {

	public DrawableRectangle(Color color, Double center, double width, double height) {
		super(color, center, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		g2g.fillRect((int) -(this.getWidth()/2), (int) -(this.getHeight()/2), 
				(int) this.getWidth(), (int) this.getHeight());
	}
	
	@Override
	public void drawOutline(Graphics2D g2g) {
		g2g.setColor(Color.YELLOW);
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.rotate(getRotation());
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		g2g.setTransform(objToWorld);
		
		g2g.drawRect((int) -(this.getWidth()/2), (int) -(this.getHeight()/2), 
				(int) this.getWidth(), (int) this.getHeight());
	}

	@Override
	public void dragShape(Graphics2D g2g, int index) {
		// TODO Auto-generated method stub
		
	}

}
