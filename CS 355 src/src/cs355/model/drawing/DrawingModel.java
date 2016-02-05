package cs355.model.drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawingModel extends CS355Drawing {

	private ArrayList<Shape> shapes = new ArrayList();
	private Shape selectedShape = null;
	
	@Override
	public Shape getShape(int index) {
		return shapes.get(index);
	}

	@Override
	public int addShape(Shape s) {
		shapes.add(s);
		this.setChanged();
		this.notifyObservers();
		return 0;
	}

	@Override
	public void deleteShape(int index) {
		shapes.remove(index);
	}

	@Override
	public void moveToFront(int index) {
		Shape shape = shapes.remove(index);
		shapes.add(0, shape);
	}

	@Override
	public void movetoBack(int index) {
		Shape shape = shapes.remove(index);
		shapes.add(shape);
	}

	@Override
	public void moveForward(int index) {
		Shape shape = shapes.remove(index);
		shapes.add(index + 1, shape);
	}

	@Override
	public void moveBackward(int index) {
		Shape shape = shapes.remove(index);
		shapes.add(index - 1, shape);
	}

	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public List<Shape> getShapesReversed() {
		ArrayList<Shape> reversedList = new ArrayList(shapes);
		Collections.reverse(reversedList);
		return reversedList;
	}

	@Override
	public void setShapes(List<Shape> shapes) {
		this.shapes = (ArrayList<Shape>) shapes;
	}
	
	public Shape getSelectedShape() {
		return selectedShape;
	}
	
	public void setSelectedShape(Shape shape) {
		selectedShape = shape;
		this.setChanged();
		this.notifyObservers();
	}

}
