package cs355.view;

import java.awt.Graphics2D;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.drawing.DrawingModel;
import cs355.model.drawing.Shape;
import drawing.DrawableShape;
import drawing.DrawableShapeFactory;

public class View implements ViewRefresher {

	private DrawableShapeFactory factory = new DrawableShapeFactory();
	private DrawingModel model;
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		
		for(Shape s: model.getShapes()){
			DrawableShape shape = factory.createShape(s);
			shape.draw(g2d);
		}
		DrawableShape selectedShape = factory.createShape(model.getSelectedShape());
		if(selectedShape != null) {
			selectedShape.drawOutline(g2d);
		}
	}

	public void addModel(DrawingModel model) {
		this.model = model;
		model.addObserver(this);
		
	}

}
