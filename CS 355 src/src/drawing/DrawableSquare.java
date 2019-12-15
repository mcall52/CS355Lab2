package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D.Double;

import Transform.Transform;
import cs355.model.drawing.Square;

public class DrawableSquare extends Square implements DrawableShape {

	private AffineTransform worldToView;

	public DrawableSquare(Color color, Double center, double size, double rotation, AffineTransform worldToView) {
		super(color, center, size);
		this.setRotation(rotation);
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
		
		g2g.fillRect((int) -(this.getSize()/2)/2, -((int) this.getSize()/2)/2, 
				(int) this.getSize()/2, (int) this.getSize()/2);
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
		
		g2g.drawRect((int) -(this.getSize()/2)/2, -((int) this.getSize()/2)/2, 
				(int) this.getSize()/2, (int) this.getSize()/2);
	}

	@Override
	public void drawHandle(Graphics2D g2g) {
		g2g.drawOval((int) -(HANDLE_RADIUS), (int) -(this.getSize()/2/2 + HANDLE_DIST), 
				(int) HANDLE_RADIUS*2, (int) HANDLE_RADIUS*2);
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
