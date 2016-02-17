package drawing;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public interface DrawableShape {

	public void draw(Graphics2D g2g);
	
	public void drawOutline(Graphics2D g2g);
		
	public void drawHandle(Graphics2D g2g);
	
	public Point2D.Double getHandleCenter();
}
