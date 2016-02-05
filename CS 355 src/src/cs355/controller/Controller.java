package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs355.GUIFunctions;
import cs355.model.drawing.CS355Drawing;
import cs355.model.drawing.Circle;
import cs355.model.drawing.DrawingModel;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;
import cs355.view.View;

public class Controller implements CS355Controller, MouseListener, MouseMotionListener {

	//private View view;
	private DrawingModel model;
	private Color curcolor;
	private View view;
	private enum CurShape{
		SELECT, CIRCLE, ELLIPSE, LINE, SQUARE, RECTANGLE, TRIANGLE
	}
	
	private Point2D.Double firstclick = null;
	private Point2D.Double secondclick = null;
	private Point2D.Double thirdclick = null;
	
	private Point2D.Double startclick;
	private Point2D.Double endclick;
	
	private CurShape curshape;
	
	public Controller(View view){
		model = new DrawingModel();
		curshape = CurShape.SELECT;
		curcolor = Color.WHITE;
		this.view = view;
		view.addModel(model);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
//		Point2D.Double point = new Point2D.Double(arg0.getX(), arg0.getY());
//		GUIFunctions.printf("Point Clicked: %s", point.toString());
//		
//		if(firstclick == null){
//			firstclick = point;
//		}
//		else if(secondclick == null){
//			secondclick = point;
//		}
//		else{
//			thirdclick = point;
//			createtriangle();
//			clearpoints();
//		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Point2D.Double point = new Point2D.Double(arg0.getX(), arg0.getY());
		GUIFunctions.printf("Point Pressed: %s", point.toString());
		startclick = (Double) point.clone();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point2D.Double point = new Point2D.Double(arg0.getX(), arg0.getY());
		GUIFunctions.printf("Point Released: %s", point.toString());
		endclick = (Double) point.clone();
		
		//Draw
		switch (curshape){
			case LINE 		:	createline(); break;
			case RECTANGLE 	:	createrectangle(); break;
			case SQUARE 	:	createsquare(); break;
			case ELLIPSE 	:	createellipse(); break;
			case CIRCLE 	:	createcircle(); break;
			case TRIANGLE	:	if(firstclick == null){
									firstclick = point;
								}
								else if(secondclick == null){
									secondclick = point;
								}
								else{
									thirdclick = point;
									createtriangle();
									clearpoints();
								} break;
			case SELECT		:	selectshape();
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		Point2D.Double point = new Point2D.Double(arg0.getX(), arg0.getY());
		Point2D.Double tempstart = startclick;
		Point2D.Double tempend = point;
		endclick = (Double) point.clone();
		
		switch (curshape){
			case LINE 		:		createline(); break;
			case RECTANGLE 	:		createrectangle(); break;
			case SQUARE 	:		createsquare(); break;
			case ELLIPSE 	:		createellipse(); break;
			case CIRCLE 	:		createcircle(); break;
		}
		if(curshape != CurShape.TRIANGLE){
			startclick = tempstart;
			endclick = tempend;
			model.deleteShape(model.getShapes().size() - 1);
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void colorButtonHit(Color c) {
		GUIFunctions.changeSelectedColor(c);
		curcolor = c;
	}

	@Override
	public void lineButtonHit() {
		curshape = CurShape.LINE;
		clearpoints();
	}

	@Override
	public void squareButtonHit() {
		curshape = CurShape.SQUARE;
		clearpoints();
	}

	@Override
	public void rectangleButtonHit() {
		curshape = CurShape.RECTANGLE;
		clearpoints();
	}

	@Override
	public void circleButtonHit() {
		curshape = CurShape.CIRCLE;
		clearpoints();
	}

	@Override
	public void ellipseButtonHit() {
		curshape = CurShape.ELLIPSE;
		clearpoints();
	}

	@Override
	public void triangleButtonHit() {
		curshape = CurShape.TRIANGLE;
		clearpoints();
	}

	@Override
	public void selectButtonHit() {
		curshape = CurShape.SELECT;
		clearpoints();
	}

	@Override
	public void zoomInButtonHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoomOutButtonHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hScrollbarChanged(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vScrollbarChanged(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggle3DModelDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openImage(File file) {
		
	}

	@Override
	public void saveImage(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggleBackgroundDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveDrawing(File file) {
		model.save(file);
	}

	@Override
	public void openDrawing(File file) {
		model.open(file);
	}

	@Override
	public void doDeleteShape() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doEdgeDetection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSharpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMedianBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doUniformBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doGrayscale() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMoveForward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMoveBackward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSendToFront() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSendtoBack() {
		// TODO Auto-generated method stub

	}
	
	//Drawing methods
	private void createtriangle() {
		Point2D.Double center = new Point2D.Double(
				averageOf(firstclick.getX(), secondclick.getX(), thirdclick.getX()), 
				averageOf(firstclick.getY(), secondclick.getY(), thirdclick.getY()));
		Triangle triangle = new Triangle(curcolor, center, firstclick, secondclick, thirdclick);
		model.addShape(triangle);
		clearpoints();
	}

	private void createcircle() {
		double width = Math.abs(startclick.getX() - endclick.getX());
		double height = Math.abs(startclick.getY() - endclick.getY());
		String longestEdge = "width";
		
		double radius = width / 2;
		if(width > height){
			radius = height / 2;
			longestEdge = "height";
		}
		Point2D.Double center;
		
		//find center for each coordinate according to shortest side (width or height)
		//1st Quadrant (bottom-right)
		if(startclick.getX() < endclick.getX() 
				&& startclick.getY() < endclick.getY()){
			center = new Point2D.Double(averageOf(startclick.getX(), startclick.getX() + radius * 2), 
					averageOf(startclick.getY(), startclick.getY() + radius * 2));
		}
		//2nd Quadrant (bottom-left)
		else if(startclick.getX() > endclick.getX()
				&& startclick.getY() < endclick.getY()){
			center = new Point2D.Double(averageOf(startclick.getX(), startclick.getX() - radius * 2), 
					averageOf(startclick.getY(), startclick.getY() + radius * 2));
		}
		//3rd Quadrant (top-left)
		else if(startclick.getX() > endclick.getX()
				&& startclick.getY() > endclick.getY()){
			center = new Point2D.Double(averageOf(startclick.getX(), startclick.getX() - radius * 2), 
					averageOf(startclick.getY(), startclick.getY() - radius * 2));		
		}
		//4th Quadrant (top-right)
		else{
			center = new Point2D.Double(averageOf(startclick.getX(), startclick.getX() + radius * 2), 
					averageOf(startclick.getY(), startclick.getY() - radius * 2));
		}
//		Point2D.Double center = new Point2D.Double(averageOf(startclick.getX(), endclick.getX()), 
//				averageOf(startclick.getY(), endclick.getY()));
		
		Circle circle = new Circle(curcolor, center, radius);
		//Ellipse ellipse = new Ellipse(curcolor, center, width, height);
		model.addShape(circle);
		clearpoints();
		
	}

	private void createellipse() {
		double width = Math.abs(startclick.getX() - endclick.getX());
		double height = Math.abs(startclick.getY() - endclick.getY());
		Point2D.Double center = new Point2D.Double(averageOf(startclick.getX(), endclick.getX()), 
				averageOf(startclick.getY(), endclick.getY()));
		
		Ellipse ellipse = new Ellipse(curcolor, center, width, height);
		model.addShape(ellipse);
		clearpoints();
	}

	private void createsquare() {
		double width = Math.abs(startclick.getX() - endclick.getX());
		double height = Math.abs(startclick.getY() - endclick.getY());
		double size;
		
		size = width * 2;
		if(height < width){
			size = height * 2;
		}
		
		Point2D.Double center;
		
		//1st Quadrant (bottom-right)
		if(startclick.getX() < endclick.getX() 
				&& startclick.getY() < endclick.getY()){
			center = new Point2D.Double(startclick.getX() + (size / 4), startclick.getY() + (size / 4));
		}
		//2nd Quadrant (bottom-left)
		else if(startclick.getX() > endclick.getX()
				&& startclick.getY() < endclick.getY()){
			center = new Point2D.Double(startclick.getX() - (size / 4), startclick.getY() + (size / 4));
		}
		//3rd Quadrant (top-left)
		else if(startclick.getX() > endclick.getX()
				&& startclick.getY() > endclick.getY()){
			center = new Point2D.Double(startclick.getX() - (size / 4), startclick.getY() - (size / 4));
		}
		//4th Quadrant (top-right)
		else{
			center = new Point2D.Double(startclick.getX() + (size / 4), startclick.getY() - (size / 4));
		}
		
		//when using upperleft
		//Square square = new Square(curcolor, upperleft, size);
		
		//when using center
		//Point2D.Double center = new Point2D.Double(upperleft.getX() + size / 4, upperleft.getY() + size / 4);
		Square square = new Square(curcolor, center, size);
		model.addShape(square);
		clearpoints();
	}

	private void createrectangle() {
		double width = Math.abs(startclick.getX() - endclick.getX());
		double height = Math.abs(startclick.getY() - endclick.getY());
		//Point2D.Double upperleft;
		Point2D.Double center;
		
		//1st Quadrant (bottom-right)
		if(startclick.getX() < endclick.getX() 
				&& startclick.getY() < endclick.getY()){
			center = new Point2D.Double(startclick.getX() + (width / 2), startclick.getY() + (height / 2));
		}
		//2nd Quadrant (bottom-left)
		else if(startclick.getX() > endclick.getX()
				&& startclick.getY() < endclick.getY()){
			center = new Point2D.Double(startclick.getX() - (width / 2), startclick.getY() + (height / 2));
		}
		//3rd Quadrant (top-left)
		else if(startclick.getX() > endclick.getX()
				&& startclick.getY() > endclick.getY()){
			center = new Point2D.Double(startclick.getX() - (width / 2), startclick.getY() - (height / 2));
		}
		//4th Quadrant (top-right)
		else{
			center = new Point2D.Double(startclick.getX() + (width / 2), startclick.getY() - (height / 2));
		}
			
		Rectangle rectangle = new Rectangle(curcolor, center, width, height);
		model.addShape(rectangle);
		clearpoints();
	}

	private void createline() {
		Line line = new Line(curcolor, startclick, endclick);
		model.addShape(line);
		clearpoints();
	}
	
	private void selectshape() {
		//use endclick
		int i = model.getShapes().size();
		boolean isInside = false;
		Shape shape;
		
		while(i > 0 && !isInside){
			i--;
			if(model.getShape(i).pointInShape(endclick, 0)){
				isInside = true;
				shape = model.getShape(i);
				//TODO draw outline and selection tab
				model.setSelectedShape(shape);
				//model.notifyObservers();
			}
		}
	}

	private void clearpoints() {
		// TODO Auto-generated method stub
		firstclick = null;
		secondclick = null;
		thirdclick = null;
		
		startclick = null;
		endclick = null;
		//model.setSelectedShape(null);
	}
	
	private double averageOf(double num1, double num2){
		double added = num1 + num2;
		return added / 2;
	}
	
	private double averageOf(double num1, double num2, double num3){
		double added = num1 + num2 + num3;
		return added / 3;
	}
}
