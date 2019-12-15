package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import Transform.Transform;
import cs355.model.drawing.Triangle;

public class DrawableTriangle extends Triangle implements DrawableShape {
	
	private AffineTransform worldToView;

	public DrawableTriangle(Color color, Double center, Double a, Double b, Double c, double rotation, AffineTransform worldToView) {
		super(color, center, a, b, c);
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
		
		int[] xpoints = new int[]{(int) (this.getA().getX()), 
				(int) (this.getB().getX()), 
				(int) (this.getC().getX())};
		int[] ypoints = new int[]{(int) (this.getA().getY()), 
				(int) (this.getB().getY()), 
				(int) (this.getC().getY())};
		g2g.fillPolygon(xpoints, ypoints, xpoints.length);
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
		
		int[] xpoints = new int[]{(int) (this.getA().getX()), 
				(int) (this.getB().getX()), 
				(int) (this.getC().getX())};
		int[] ypoints = new int[]{(int) (this.getA().getY()), 
				(int) (this.getB().getY()), 
				(int) (this.getC().getY())};
		g2g.drawPolygon(xpoints, ypoints, xpoints.length);
	}

	@Override
	public void drawHandle(Graphics2D g2g) {
		//find the highest point
		g2g.drawOval((int) -(HANDLE_RADIUS), (int) -(getHighestPoint().getY() + HANDLE_DIST), 
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
