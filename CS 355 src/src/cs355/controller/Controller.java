package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;
import java.util.Iterator;

import Transform.Transform;
import cs355.GUIFunctions;
import cs355.model.drawing.Circle;
import cs355.model.drawing.DrawingModel;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;
import cs355.model.image.CS355Image;
import cs355.model.scene.CS355Scene;
import cs355.model.scene.Point3D;
import cs355.view.View;

public class Controller implements CS355Controller, MouseListener, MouseMotionListener {

	private final double STARTING_VIEW_POINT = 768;
	
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
	
	private Point2D.Double starttocenter;
	
	private CurShape curshape;
	private boolean handleSelected;
	
	private Point2D.Double viewupperleft;
	
	//3D Variables 
	private final int W = 87;
	private final int A = 65;
	private final int S = 83;
	private final int D = 68;
	private final int Q = 81;
	private final int E = 69;
	private final int R = 82;
	private final int F = 70;
	private final int H = 72;
	
	private boolean is3d = false;
	private Point3D campos = new Point3D(0, -3, 0);
	private float camangle = 0;
	private Point3D initialcampos = new Point3D(0, -3, 0);
	private float initialcamrot;
	private CS355Scene scene = new CS355Scene();
	
	//Lab 6 variables
	private CS355Image image = new DrawingImage();
	
	public Controller(View view){
		model = new DrawingModel();
		curshape = CurShape.SELECT;
		curcolor = Color.WHITE;
		this.view = view;
		view.addModel(model);
		viewupperleft = new Point2D.Double(STARTING_VIEW_POINT, STARTING_VIEW_POINT);
		
		image.deleteObservers();
		image.addObserver(view);
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
//		GUIFunctions.printf("Point Pressed: %s", point.toString());
		startclick = point;
		switch (curshape){
			case SELECT		:	selectshape();
			default			: 	//do nothing
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point2D.Double point = new Point2D.Double(arg0.getX(), arg0.getY());
//		GUIFunctions.printf("Point Released: %s", point.toString());
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
			case SELECT		:	handleSelected = false;
			default 		:	//do nothing
		}
		starttocenter = null;
		
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
			case SELECT		:		if(handleSelected){
										rotateShape(point);
									}
									else {
										dragShape(point); //break;
									}
		}
		if(curshape != CurShape.TRIANGLE && curshape != CurShape.SELECT){
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
		if(model.getSelectedShape() != null) {
			model.getSelectedShape().setColor(c);
			model.outsideChange();
			model.notifyObservers();
		}
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
		if(view.getScaleFactor() < 4){
			view.zoomIn();
			
			Point2D.Double newupperleft = 
					new Point2D.Double(viewupperleft.x + ((512/view.getScaleFactor())/2),
							viewupperleft.y + ((512/view.getScaleFactor())/2));
			viewupperleft = newupperleft;
			
			view.setViewUpperLeft(viewupperleft);
			doZoom();
		}
	}

	@Override
	public void zoomOutButtonHit() {
		if(view.getScaleFactor() > .25){
			view.zoomOut();
			
			Point2D.Double newupperleft = 
					new Point2D.Double(viewupperleft.x - ((512/view.getScaleFactor())/2),
							viewupperleft.y - ((512/view.getScaleFactor())/2));
			if(newupperleft.x < 0){
				newupperleft.x = 0;
			}
			if(newupperleft.y < 0){
				newupperleft.y = 0;
			}
			if(newupperleft.x + (512/view.getScaleFactor()) > 2048){
				newupperleft.x = 2048 - (512/view.getScaleFactor());
			}
			if(newupperleft.y + (512/view.getScaleFactor()) > 2048){
				newupperleft.y = 2048 - (512/view.getScaleFactor());
			}
			
			view.setViewUpperLeft(viewupperleft);
			doZoom();
		}
	}
	
	public void doZoom(){
		int barsize = (int) (512 / view.getScaleFactor());
		int hscrollpos = (int) viewupperleft.x;
		int vscrollpos = (int) viewupperleft.y;

		GUIFunctions.setZoomText(view.getScaleFactor());
		
		GUIFunctions.setHScrollBarPosit(hscrollpos);
		GUIFunctions.setHScrollBarKnob(barsize);

		GUIFunctions.setVScrollBarPosit(vscrollpos);
		GUIFunctions.setVScrollBarKnob(barsize);
		GUIFunctions.refresh();
		
	}

	@Override
	public void hScrollbarChanged(int value) {
		viewupperleft.x = value;
		GUIFunctions.refresh();
		
	}

	@Override
	public void vScrollbarChanged(int value) {
		viewupperleft.y = value;
		GUIFunctions.refresh();
	}

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub
//		CS355Scene scene = new CS355Scene();
		
		scene.open(file);
		view.setScene(scene);
		view.setCamPos(scene.getCameraPosition());
		campos = scene.getCameraPosition();
		initialcampos = scene.getCameraPosition();
		initialcamrot = (float) scene.getCameraRotation();
		GUIFunctions.refresh();
	}

	@Override
	public void toggle3DModelDisplay() {
		is3d = true;
		view.is3d = true;
	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		if(is3d){
			switch(iterator.next()){
			case (W)	:	campos.x -= 1 * (float)Math.sin(camangle);
        					campos.z += 1 * (float)Math.cos(camangle);
//        					view.setCamPos(campos);
        					scene.setCameraPosition(campos);
        					GUIFunctions.refresh();
        					break;
        					
			case (A)	:	campos.x += 1 *  (float)Math.cos(camangle);
        					campos.z += 1 * (float)Math.sin(camangle);
//        					view.setCamPos(campos);
        					scene.setCameraPosition(campos);
        					GUIFunctions.refresh();
        					break;
        					
			case (S)	:	campos.x += 1 *  (float)Math.sin(camangle);
        					campos.z -= 1 * (float)Math.cos(camangle);
//        					view.setCamPos(campos);
        					scene.setCameraPosition(campos);
        					GUIFunctions.refresh();
        					break;
        					
			case (D)	:	campos.x -= 1 *  (float)Math.cos((camangle));
        					campos.z -= 1 * (float)Math.sin((camangle));
//        					view.setCamPos(campos);
        					scene.setCameraPosition(campos);
        					GUIFunctions.refresh();
        					break;
        					
			case (Q)	:	camangle += -Math.PI/16;
//							view.setCamangle(camangle);
							scene.setCameraRotation(camangle);
							GUIFunctions.refresh();
							break;
							
			case (E)	:	camangle += Math.PI/16;
//							view.setCamangle(camangle);
							scene.setCameraRotation(camangle);
							GUIFunctions.refresh();
							break;
							
			case (R)	:	campos.y += Math.PI/16;
//							view.setCamPos(campos);
							scene.setCameraRotation(camangle);
							GUIFunctions.refresh();
							break;
							
			case (F)	:	campos.y += -Math.PI/16;
//							view.setCamPos(campos);
							scene.setCameraRotation(camangle);
							GUIFunctions.refresh();
							break;
							
			case (H)	:	//set default values
							campos = initialcampos;
							camangle = initialcamrot;
							scene.setCameraPosition(campos);
							scene.setCameraRotation(camangle);
							GUIFunctions.refresh();
							break;
				default 	:	//do nothing
			}
		}
	}

	@Override
	public void openImage(File file) {
		image.open(file);
		view.setImage(image);
	}

	@Override
	public void saveImage(File file) {
		image.save(file);
		
	}

	@Override
	public void toggleBackgroundDisplay() {
		view.toggleDrawImage();
		GUIFunctions.refresh();
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
		model.deleteShape(model.getSelectedShapeIndex());
		model.outsideChange();
		model.notifyObservers();
	}

	@Override
	public void doEdgeDetection() {
		// TODO Auto-generated method stub
		image.edgeDetection();
	}

	@Override
	public void doSharpen() {
		// TODO Auto-generated method stub
		image.sharpen();
	}

	@Override
	public void doMedianBlur() {
		// TODO Auto-generated method stub
		image.medianBlur();
	}

	@Override
	public void doUniformBlur() {
		// TODO Auto-generated method stub
		image.uniformBlur();
	}

	@Override
	public void doGrayscale() {
		// TODO Auto-generated method stub
		image.grayscale();
	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		// TODO Auto-generated method stub
		image.contrast(contrastAmountNum);
	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		// TODO Auto-generated method stub
		image.brightness(brightnessAmountNum);
	}

	@Override
	public void doMoveForward() {
		// TODO Auto-generated method stub
		model.moveForward(model.getSelectedShapeIndex());
	}

	@Override
	public void doMoveBackward() {
		// TODO Auto-generated method stub
		model.moveBackward(model.getSelectedShapeIndex());
	}

	@Override
	public void doSendToFront() {
		// TODO Auto-generated method stub
		model.moveToFront(model.getSelectedShapeIndex());
	}

	@Override
	public void doSendtoBack() {
		// TODO Auto-generated method stub
		model.movetoBack(model.getSelectedShapeIndex());
	}
	
	//Drawing methods
	private void createtriangle() {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldfirst = new Point2D.Double(); 
		Point2D.Double worldsecond = new Point2D.Double();
		Point2D.Double worldthird = new Point2D.Double();
		viewToWorld.transform(firstclick, worldfirst);
		viewToWorld.transform(secondclick, worldsecond);
		viewToWorld.transform(thirdclick, worldthird);

		Point2D.Double center = new Point2D.Double(
				averageOf(worldfirst.getX(), worldsecond.getX(), worldthird.getX()), 
				averageOf(worldfirst.getY(), worldsecond.getY(), worldthird.getY()));
		
		Point2D.Double a = new Point2D.Double(worldfirst.getX() - center.getX(), 
				worldfirst.getY() - center.getY());
		Point2D.Double b = new Point2D.Double(worldsecond.getX()- center.getX(),
				worldsecond.getY() - center.getY());
		Point2D.Double c = new Point2D.Double(worldthird.getX() - center.getX(), 
				worldthird.getY() - center.getY());
		
		Triangle triangle = new Triangle(curcolor, center, a, b, c);
		model.addShape(triangle);
		clearpoints();
	}

	private void createcircle() {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
		Point2D.Double worldend = new Point2D.Double();
		viewToWorld.transform(startclick, worldstart);
		viewToWorld.transform(endclick, worldend);

		double width = Math.abs(worldstart.getX() - worldend.getX());
		double height = Math.abs(worldstart.getY() - worldend.getY());
		String longestEdge = "width";
		
		double radius = width / 2;
		if(width > height){
			radius = height / 2;
			longestEdge = "height";
		}
		Point2D.Double center;
		
		//find center for each coordinate according to shortest side (width or height)
		//1st Quadrant (bottom-right)
		if(worldstart.getX() < worldend.getX() 
				&& worldstart.getY() < worldend.getY()){
			center = new Point2D.Double(averageOf(worldstart.getX(), worldstart.getX() + radius * 2), 
					averageOf(worldstart.getY(), worldstart.getY() + radius * 2));
		}
		//2nd Quadrant (bottom-left)
		else if(worldstart.getX() > worldend.getX()
				&& worldstart.getY() < worldend.getY()){
			center = new Point2D.Double(averageOf(worldstart.getX(), worldstart.getX() - radius * 2), 
					averageOf(worldstart.getY(), worldstart.getY() + radius * 2));
		}
		//3rd Quadrant (top-left)
		else if(worldstart.getX() > worldend.getX()
				&& worldstart.getY() > worldend.getY()){
			center = new Point2D.Double(averageOf(worldstart.getX(), worldstart.getX() - radius * 2), 
					averageOf(worldstart.getY(), worldstart.getY() - radius * 2));		
		}
		//4th Quadrant (top-right)
		else{
			center = new Point2D.Double(averageOf(worldstart.getX(), worldstart.getX() + radius * 2), 
					averageOf(worldstart.getY(), worldstart.getY() - radius * 2));
		}
//		Point2D.Double center = new Point2D.Double(averageOf(startclick.getX(), endclick.getX()), 
//				averageOf(startclick.getY(), endclick.getY()));
		
		Circle circle = new Circle(curcolor, center, radius);
		//Ellipse ellipse = new Ellipse(curcolor, center, width, height);
		model.addShape(circle);
		clearpoints();
		
	}

	private void createellipse() {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
		Point2D.Double worldend = new Point2D.Double();
		viewToWorld.transform(startclick, worldstart);
		viewToWorld.transform(endclick, worldend);
		
		double width = Math.abs(worldstart.getX() - worldend.getX());
		double height = Math.abs(worldstart.getY() - worldend.getY());
		Point2D.Double center = new Point2D.Double(averageOf(worldstart.getX(), worldend.getX()), 
				averageOf(worldstart.getY(), worldend.getY()));
		
		Ellipse ellipse = new Ellipse(curcolor, center, width, height);
		model.addShape(ellipse);
		clearpoints();
	}

	private void createsquare() {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
		Point2D.Double worldend = new Point2D.Double();
		viewToWorld.transform(startclick, worldstart);
		viewToWorld.transform(endclick, worldend);
		
		double width = Math.abs(worldstart.getX() - worldend.getX());
		double height = Math.abs(worldstart.getY() - worldend.getY());
		double size;
		
		size = width * 2;
		if(height < width){
			size = height * 2;
		}
		
		Point2D.Double center;
		
		//1st Quadrant (bottom-right)
		if(worldstart.getX() < worldend.getX() 
				&& worldstart.getY() < worldend.getY()){
			center = new Point2D.Double(worldstart.getX() + (size / 4), worldstart.getY() + (size / 4));
		}
		//2nd Quadrant (bottom-left)
		else if(worldstart.getX() > worldend.getX()
				&& worldstart.getY() < worldend.getY()){
			center = new Point2D.Double(worldstart.getX() - (size / 4), worldstart.getY() + (size / 4));
		}
		//3rd Quadrant (top-left)
		else if(worldstart.getX() > worldend.getX()
				&& worldstart.getY() > worldend.getY()){
			center = new Point2D.Double(worldstart.getX() - (size / 4), worldstart.getY() - (size / 4));
		}
		//4th Quadrant (top-right)
		else{
			center = new Point2D.Double(worldstart.getX() + (size / 4), worldstart.getY() - (size / 4));
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
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
		Point2D.Double worldend = new Point2D.Double();
		viewToWorld.transform(startclick, worldstart);
		viewToWorld.transform(endclick, worldend);
		
		double width = Math.abs(worldstart.getX() - worldend.getX());
		double height = Math.abs(worldstart.getY() - worldend.getY());
		//Point2D.Double upperleft;
		Point2D.Double center;
		
		//1st Quadrant (bottom-right)
		if(worldstart.getX() < worldend.getX() 
				&& worldstart.getY() < worldend.getY()){
			center = new Point2D.Double(worldstart.getX() + (width / 2), worldstart.getY() + (height / 2));
		}
		//2nd Quadrant (bottom-left)
		else if(worldstart.getX() > worldend.getX()
				&& worldstart.getY() < worldend.getY()){
			center = new Point2D.Double(worldstart.getX() - (width / 2), worldstart.getY() + (height / 2));
		}
		//3rd Quadrant (top-left)
		else if(worldstart.getX() > worldend.getX()
				&& worldstart.getY() > worldend.getY()){
			center = new Point2D.Double(worldstart.getX() - (width / 2), worldstart.getY() - (height / 2));
		}
		//4th Quadrant (top-right)
		else{
			center = new Point2D.Double(worldstart.getX() + (width / 2), worldstart.getY() - (height / 2));
		}
			
		Rectangle rectangle = new Rectangle(curcolor, center, width, height);
		model.addShape(rectangle);
		clearpoints();
	}

	private void createline() {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
		Point2D.Double worldend = new Point2D.Double();
		viewToWorld.transform(startclick, worldstart);
		viewToWorld.transform(endclick, worldend);
		
		Line line = new Line(curcolor, worldstart, new Point2D.Double(worldend.getX() - worldstart.getX(), 
				worldend.getY() - worldstart.getY()));
		model.addShape(line);
		clearpoints();
	}
	
	private void selectshape() {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
//		Point2D.Double worldend = new Point2D.Double();
		viewToWorld.transform(startclick, worldstart);
//		viewToWorld.transform(endclick, worldend);
		
		//use endclick
		int i = model.getShapes().size();
		boolean isInside = false;
		Shape shape;
		
		if(model.getSelectedShape() != null && model.getSelectedShape().pointInHandle(worldstart, 0)){
//			System.out.println("selected!");
			handleSelected = true;
			//rotateShape();
		}
		else{
			while(i > 0 && !isInside){
				i--;
				if(model.getShape(i).pointInShape(worldstart, 4)){
					isInside = true;
					shape = model.getShape(i);
					//TODO draw outline and selection tab
					model.setSelectedShape(shape);
					model.setSelectedShapeIndex(i);
					//change color
					GUIFunctions.changeSelectedColor(shape.getColor());
					curcolor = shape.getColor();
				}
				else{
					model.setSelectedShape(null);
					model.setSelectedShapeIndex(-1);
				}
			}
		}
	}
	
	private void dragShape(Point2D.Double point) {
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
		
		Point2D.Double worldstart = new Point2D.Double(); 
		Point2D.Double worldpoint = new Point2D.Double();
		
		viewToWorld.transform(startclick, worldstart);
		viewToWorld.transform(endclick, worldpoint);
		
		Shape shape = model.getSelectedShape();
		//find difference between center of shape and clicked point
		if(starttocenter == null){
			starttocenter = new Point2D.Double(shape.getCenter().getX() - worldstart.getX(), 
					shape.getCenter().getY() - worldstart.getY());
		}
		//change center to be the difference between the clicked point and the original center
		Point2D.Double draggedcenter = new Point2D.Double(starttocenter.getX() + worldpoint.getX(),
				starttocenter.getY() + worldpoint.getY());
		shape.setCenter(draggedcenter);

		model.outsideChange();
		model.notifyObservers();
	}
	
	private void rotateShape(Point2D.Double pt){
		AffineTransform viewToWorld = Transform.viewToWorld(viewupperleft.x, viewupperleft.y, view.getScaleFactor());
				
		//get current shape
		Shape shape = model.getSelectedShape();
		//World to Object Transformation
		Point2D.Double objpt = new Point2D.Double();
		Point2D.Double clickedpt = new Point2D.Double();
		
		AffineTransform worldToObj = Transform.worldToObj(shape.getCenter().x, 
				shape.getCenter().y, shape.getRotation());
		worldToObj.concatenate(viewToWorld);
//		AffineTransform rotate = new AffineTransform()
//		worldToObj.rotate(-shape.getRotation());
//		worldToObj.translate(-shape.getCenter().getX(), -shape.getCenter().getY());
		worldToObj.transform(pt, objpt);
		worldToObj.transform(startclick, clickedpt);
		
		if(shape instanceof Line){
			if(shape.pointInHandle(objpt, 0)){ //startHandle selected
				//TODO change center of line but keep end in same spot
			}
			else{ //endHandle selected
				//TODO change endpoint. Should be easy
			}
		}
		else{
			//calculate rotation
			shape.setRotation(Math.atan2(objpt.getY(), objpt.getX()) 
					- Math.atan2(clickedpt.getY(), clickedpt.getX()));
			//System.out.println(shape.getRotation()); //TODO check translate path 
		}
		model.outsideChange();
		model.notifyObservers();
		GUIFunctions.refresh();
		//Object to World Transformation
//		AffineTransform objToWorld = new AffineTransform();
//		objToWorld.rotate(shape.getRotation());
//		objToWorld.translate(shape.getCenter().getX(), shape.getCenter().getY());
		//g2g.setTransform(objToWorld);
		
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
