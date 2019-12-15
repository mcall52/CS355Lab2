package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import Transform.Transform;
import cs355.model.drawing.Rectangle;

public class DrawableRectangle extends Rectangle implements DrawableShape {

	private AffineTransform worldToView;
	
	public DrawableRectangle(Color color, Double center, double width, double height, double rotation, AffineTransform worldToView) {
		super(color, center, width, height);
		this.setRotation(rotation);
		this.worldToView = worldToView;
	}

	@Override
	public void draw(Graphics2D g2g) {
		g2g.setColor(this.getColor());
		AffineTransform objToWorld = 
				Transform.objToWorld(getCenter().getX(), getCenter().getY(), getRotation());
		worldToView.concatenate(objToWorld);
		//System.out.println(getRotation());
//		objToWorld.translate(getCenter().getX(), getCenter().getY());
//		objToWorld.rotate(getRotation());
		g2g.setTransform(worldToView);
//		System.out.println("Center at: (" + center.x + ", " + center.y + ")");
		g2g.fillRect((int) -(this.getWidth()/2), (int) -(this.getHeight()/2), 
				(int) this.getWidth(), (int) this.getHeight());
	}
	
	@Override
	public void drawOutline(Graphics2D g2g) {
		g2g.setColor(Color.YELLOW);
		AffineTransform objToWorld = 
				Transform.objToWorld(getCenter().getX(), getCenter().getY(), getRotation());
		worldToView.concatenate(objToWorld);
//		AffineTransform worldToView = 
//				Transform.worldToView(viewupperleftx, viewupperlefty, scalefactor)
//		objToWorld.translate(getCenter().getX(), getCenter().getY());
//		objToWorld.rotate(getRotation());
		g2g.setTransform(worldToView);
		drawHandle(g2g);
		
		g2g.drawRect((int) -(this.getWidth()/2), (int) -(this.getHeight()/2), 
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
	
	public void setWorldToView(AffineTransform af){
		worldToView = af;
	}
	
}
