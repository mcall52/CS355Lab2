package Transform;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs355.model.scene.Point3D;

public class Transform {
//	public static AffineTransform translate(double x, double y){
//		AffineTransform translate = new AffineTransform(1, 0, 0, 1, x, y);
//		return translate;
//	}
//	
//	public static AffineTransform rotate(){
//		return new AffineTransform();		
//	}
	
	public static AffineTransform objToWorld(double centerx, double centery, double rotation){
//		AffineTransform objToWorld = new AffineTransform();
		AffineTransform translate = new AffineTransform(1, 0, 0, 1, centerx, centery);
		AffineTransform rotate = new AffineTransform(Math.cos(rotation), Math.sin(rotation), 
				-Math.sin(rotation), Math.cos(rotation), 0, 0);
		//objToWorld.translate(centerx, centery);
		translate.concatenate(rotate);
//		objToWorld =  translate;
		//objToWorld.rotate(rotation);
		
		return translate;
	}
	
	public static AffineTransform worldToObj(double centerx, double centery, double rotation){
//		AffineTransform objToWorld = new AffineTransform();
		AffineTransform translate = new AffineTransform(1, 0, 0, 1, -centerx, -centery);
		AffineTransform rotate = new AffineTransform(Math.cos(rotation), -Math.sin(rotation),
				Math.sin(rotation), Math.cos(rotation), 0, 0);
//		objToWorld.rotate(-rotation);
//		objToWorld.translate(-centerx, -centery);
		rotate.concatenate(translate);
		return rotate;
	}
	
	public static AffineTransform worldToView(double viewupperleftx, double viewupperlefty, double scalefactor){
		AffineTransform translate = new AffineTransform(1, 0, 0, 1, -viewupperleftx, -viewupperlefty);
		AffineTransform scale = new AffineTransform(scalefactor, 0, 0, scalefactor, 0, 0);
		scale.concatenate(translate);

		return scale;
	}
	
	public static AffineTransform viewToWorld(double viewupperleftx, double viewupperlefty, double scalefactor){
		AffineTransform translate = new AffineTransform(1, 0, 0, 1, viewupperleftx, viewupperlefty);
		AffineTransform scale = new AffineTransform(1/scalefactor, 0, 0, 1/scalefactor, 0, 0);
		translate.concatenate(scale);
		
		return translate;
	}
	
	public static Matrix3D worldToCamera(Point3D camera, float camerarot){
		//multiply Clip * CameraRotation * CamperaPos * WorldTranslate * WorldRotate * PointVector
//		Matrix3D clip = new Matrix3D();
//		clip.setValue(2, 2, 1001/999);
//		clip.setValue(2, 3, -2000/999);
//		clip.setValue(3, 2, 1);
//		clip.setValue(3, 3, 0);
		Matrix3D cameraRot = new Matrix3D(camerarot);
		Matrix3D cameraPos = new Matrix3D((float) -camera.x, (float) -camera.y, (float) -camera.z);
		
		
		Matrix3D result = cameraRot;
//		result.multiply(cameraRot);
		result.multiply(cameraPos);
		
		
		return result;
	}
	
	public static float[] objToWorld3D(Point3D point, float rotation, Point3D instpos){
		Matrix3D worldTran = new Matrix3D((float) instpos.x, (float) instpos.y, (float) instpos.z);
		Matrix3D worldRot = new Matrix3D((double)rotation);
		float[] vector = {(float) point.x, (float) point.y, (float) point.z, 1};
		
		Matrix3D result = worldTran;
		result.multiply(worldRot);
		
		return result.multiply(vector);
	}
	
	public static Matrix3D getClip(double far, double near){
		Matrix3D clip = new Matrix3D();
		clip.setValue(2, 2, (float) ((far+near)/(far-near)));
		clip.setValue(2, 3, (float) (-(2*far*near)/(far-near)));
		clip.setValue(3, 2, 1);
		clip.setValue(3, 3, 0);
		
		return clip;
	}
	
	public static Point2D.Double screenTran(float[] point){
		float[] screentran = new float[9];
//		screentran[0] = 1024;
		screentran[0] = 256;
		screentran[1] = 0;
//		screentran[2] = 1024;
		screentran[2] = 256;
		screentran[3] = 0;
//		screentran[4] = -1024;
		screentran[4] = -256;
//		screentran[5] = 1024;
		screentran[5] = 256;
		screentran[6] = 0;
		screentran[7] = 0; 
		screentran[8] = 1;
		
		double x = (point[0] / point[3] * screentran[0]) +
//				(point[1] / point[3] * screentran[1]) +
				(screentran[2] * 1);
		double y = //	(point[0] / point[3]* screentran[3]) +
				(point[1] / point[3] * screentran[4]) +
				(1 * screentran[5]);
//		double z = (point.x / 1024 * screentran[6]) +
//				(point.y / 1024 * screentran[7]) +
//				(1 * screentran[8]);
		return new Point2D.Double(x, y);
	}
	
}
