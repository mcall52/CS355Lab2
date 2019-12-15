package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import Transform.Transform;
import cs355.model.drawing.Circle;

public class DrawableCircle extends Circle implements DrawableShape {

	Point2D.Double upperleft;
	private AffineTransform worldToView;
	
	public DrawableCircle(Color color, Double center, double radius, AffineTransform worldToView) {
		super(color, center, radius);
		// TODO Auto-generated constructor stub
		this.upperleft = new Point2D.Double(center.getX() - radius, center.getY() - radius);
		this.worldToView = worldToView;
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = 
				Transform.objToWorld(getCenter().getX(), getCenter().getY(), getRotation());
		worldToView.concatenate(objToWorld);
//		objToWorld.translate(getCenter().getX(), getCenter().getY());
//		objToWorld.rotate(getRotation());
		g2g.setTransform(worldToView);
		
		g2g.fillOval((int) -(this.getRadius()), (int) -(this.getRadius()), 
				(int) this.getRadius()*2, (int) this.getRadius()*2);
	}

	@Override
	public void drawOutline(Graphics2D g2g) {
		g2g.setColor(Color.YELLOW);
		AffineTransform objToWorld = 
				Transform.objToWorld(getCenter().getX(), getCenter().getY(), getRotation());
		worldToView.concatenate(objToWorld);
//		objToWorld.translate(getCenter().getX(), getCenter().getY());
//		objToWorld.rotate(getRotation());
		g2g.setTransform(worldToView);
//		drawHandle(g2g);
		
		g2g.drawOval((int) -(this.getRadius()), (int) -(this.getRadius()), 
				(int) this.getRadius()*2, (int) this.getRadius()*2);
	}

	@Override
	public void drawHandle(Graphics2D g2g) {		
//		g2g.drawOval((int) -(HANDLE_RADIUS), (int) -(this.getRadius() + 20), 
//				(int) HANDLE_RADIUS*2, (int) HANDLE_RADIUS*2);	
	}

	@Override
	public Double getHandleCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWorldToView(AffineTransform af) {
		worldToView = af;
	}

}
