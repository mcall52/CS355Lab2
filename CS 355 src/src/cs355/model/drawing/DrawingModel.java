package cs355.model.drawing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawingModel extends CS355Drawing {

	private ArrayList<Shape> shapes = new ArrayList();
	private Shape selectedShape = null;
	private int selectedShapeIndex = -1;
	
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
		if(index != -1){
			shapes.remove(index);
			selectedShape = null;
			selectedShapeIndex = -1;
		}
	}

	@Override
	public void moveToFront(int index) {
		if(index != -1){
			Shape shape = shapes.remove(index);
			shapes.add(shape);
			selectedShapeIndex = shapes.size() - 1;
			this.setChanged();
			this.notifyObservers();
		}
	}

	@Override
	public void movetoBack(int index) {
		if(index != -1){
			Shape shape = shapes.remove(index);
			shapes.add(0, shape);
			selectedShapeIndex = 0;
			this.setChanged();
			this.notifyObservers();
		}
	}

	@Override
	public void moveForward(int index) {
		if(index != -1 && index != shapes.size()){
			Shape shape = shapes.remove(index);
			shapes.add(index + 1, shape);
			selectedShapeIndex++;
			this.setChanged();
			this.notifyObservers();
		}
	}

	@Override
	public void moveBackward(int index) {
		if(index > 0){
			Shape shape = shapes.remove(index);
			shapes.add(index - 1, shape);
			selectedShapeIndex--;
			this.setChanged();
			this.notifyObservers();
		}
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

	public int getSelectedShapeIndex() {
		return selectedShapeIndex;
	}
	
	public void setSelectedShapeIndex(int index) {
		selectedShapeIndex = index;
	}
	
	public void outsideChange(){
		this.setChanged();
	}
}
