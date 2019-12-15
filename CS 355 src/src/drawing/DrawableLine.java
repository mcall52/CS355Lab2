package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import Transform.Transform;
import cs355.model.drawing.Line;

public class DrawableLine extends Line implements DrawableShape {
	
	private AffineTransform worldToView;

	public DrawableLine(Color color, Double start, Double end, AffineTransform worldToView) {
		super(color, start, end);
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
		// TODO FIX ME TOO YO
		g2g.setTransform(new AffineTransform());
		
		g2g.drawLine(0, 0,(int) (this.getEnd().getX()), 
				(int) (this.getEnd().getY()));
		
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
		drawHandle(g2g);
		
		g2g.drawOval((int) -HANDLE_RADIUS, (int) -HANDLE_RADIUS, 
				(int) HANDLE_RADIUS * 2, (int) HANDLE_RADIUS * 2);
	}

	@Override
	public void drawHandle(Graphics2D g2g) {
		g2g.drawOval((int) (this.getEnd().getX() - HANDLE_RADIUS), (int) (this.getEnd().getY() - HANDLE_RADIUS), 
				(int) HANDLE_RADIUS * 2, (int) HANDLE_RADIUS * 2);
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
