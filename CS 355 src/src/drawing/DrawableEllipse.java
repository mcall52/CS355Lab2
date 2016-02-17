package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Ellipse;

public class DrawableEllipse extends Ellipse implements DrawableShape {
	
	private Point2D.Double upperleft;

	public DrawableEllipse(Color color, Double center, double width, double height) {
		super(color, center, width, height);
		// TODO Auto-generated constructor stub
		this.upperleft = new Point2D.Double(center.getX() - (width/2), center.getY() - (height/2));
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		objToWorld.rotate(getRotation());
		g2g.setTransform(objToWorld);
		
		g2g.fillOval((int) -(this.getWidth()/2), (int) -(this.getHeight()/2), 
				(int) this.getWidth(), (int) this.getHeight());
	}

	@Override
	public void drawOutline(Graphics2D g2g) {
		g2g.setColor(Color.YELLOW);
		AffineTransform objToWorld = new AffineTransform();
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		objToWorld.rotate(getRotation());
		g2g.setTransform(objToWorld);
		drawHandle(g2g);
		
		g2g.drawOval((int) -(this.getWidth()/2), (int) -(this.getHeight()/2), 
				(int) this.getWidth(), (int) this.getHeight());
	}

	@Override
	public void drawHandle(Graphics2D g2g) {
		g2g.drawOval((int) -(HANDLE_RADIUS), (int) -(this.getHeight()/2 + HANDLE_DIST), 
				(int) HANDLE_RADIUS*2, (int) HANDLE_RADIUS*2);
	}

	@Override
	public Double getHandleCenter() {
		// TODO Auto-generated method stub
		return null;
	}

}
