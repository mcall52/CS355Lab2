package drawing;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

import cs355.GUIFunctions;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;

public class DrawableShapeFactory {

	public DrawableShape createShape(Shape shape){
		
		//find the type of shape using instanceof and create an instance of the Drawable Shape
		DrawableShape newshape;
		
		if (shape instanceof Line){
			newshape = new DrawableLine(shape.getColor(), ((Line) shape).getStart(), ((Line) shape).getEnd());
			GUIFunctions.printf("%s", "Line Drawn");
		}
		else if(shape instanceof Square){
			newshape = new DrawableSquare(shape.getColor(), ((Square) shape).getCenter(), ((Square) shape).getSize());
		}
		else if(shape instanceof Rectangle){
			newshape = new DrawableRectangle(shape.getColor(), ((Rectangle) shape).getCenter(), ((Rectangle) shape).getWidth(), ((Rectangle) shape).getHeight());
		}
		else if(shape instanceof Circle){
			newshape = new DrawableCircle(shape.getColor(), ((Circle) shape).getCenter(), ((Circle) shape).getRadius());
		}
		else if(shape instanceof Ellipse){
			newshape = new DrawableEllipse(shape.getColor(), ((Ellipse) shape).getCenter(), ((Ellipse) shape).getWidth(), ((Ellipse) shape).getHeight());
		}
		else if(shape instanceof Triangle){
			newshape = new DrawableTriangle(shape.getColor(), ((Triangle) shape).getCenter(), ((Triangle) shape).getA(), ((Triangle) shape).getB(), ((Triangle) shape).getC());
		}
		else{
			newshape = null;
		}
		return newshape;
	}

}
