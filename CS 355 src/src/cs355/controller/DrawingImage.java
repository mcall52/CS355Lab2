package cs355.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import cs355.model.image.CS355Image;

public class DrawingImage extends CS355Image {
	
	private boolean drawImage = false;
	private DrawingImage buffer;
	
	private float[] hsbpos0 = new float[3];
	private float[] hsbpos1 = new float[3];
	private float[] hsbpos2 = new float[3];
	private float[] hsbpos3 = new float[3];
	private float[] hsbpos4 = new float[3];
	private float[] hsbpos5 = new float[3];
	private float[] hsbpos6 = new float[3];
	private float[] hsbpos7 = new float[3];
	private float[] hsbpos8 = new float[3];
	
	private int[] rgbpos0 = new int[3];
	private int[] rgbpos1 = new int[3];
	private int[] rgbpos2 = new int[3];
	private int[] rgbpos3 = new int[3];
	private int[] rgbpos4 = new int[3];
	private int[] rgbpos5 = new int[3];
	private int[] rgbpos6 = new int[3];
	private int[] rgbpos7 = new int[3];
	private int[] rgbpos8 = new int[3];

	@Override
	public void notifyObservers(){
		super.setChanged();
		super.notifyObservers();
	}
	
	@Override
	public BufferedImage getImage() {
        if (drawImage)
        {
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            WritableRaster raster = image.getRaster();
            int[] rgb = new int[4];
            for (int y = 0; y < getHeight(); y++)
            {
                for (int x = 0; x < getWidth(); x++)
                {
                    getPixel(x, y, rgb);
                    raster.setPixel(x, y, rgb);
                }
            }
            return image;
        }
        else
            return null;
	}

	@Override
	public void edgeDetection() {
		buffer = new DrawingImage();
		buffer.setPixels(this.getImage());
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				buffer.getPixel(x, y, rgb);
				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
				float xsob = 0;
				float ysob = 0;
				
				setSurroundingPixels(x, y);
				xsob += (hsbpos0[2]*-1);
				xsob += (hsbpos2[2]);
				xsob += (hsbpos3[2]*-2);
				xsob += (hsbpos5[2]*2);
				xsob += (hsbpos6[2]*-1);
				xsob += (hsbpos8[2]);
				
				xsob = xsob/8f;
				
				ysob += (hsbpos0[2]*-1);
				ysob += (hsbpos1[2]*-2);
				ysob += (hsbpos2[2]*-1);
				ysob += (hsbpos6[2]);
				ysob += (hsbpos7[2]*2);
				ysob += (hsbpos8[2]);
				
				ysob = ysob/8f;
				
				float xydist = (float) Math.sqrt(Math.pow(xsob, 2) + Math.pow(ysob, 2));
				
				//clip between 0 and 1
				if(xydist > 1){
					xydist = 1;
				}
				if(xydist < 0){
					xydist = 0;
				}
				//get int of cipping * 256
				int clipped = (int) (xydist * 255);
				
				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], 0, clipped);
				rgb[0] = clipped;
				rgb[1] = clipped;
				rgb[2] = clipped;
				// Set the pixel.
				setPixel(x, y, rgb);
			}	
		}
//		this.setPixels(buffer.getImage());
		this.notifyObservers();
	}

	@Override
	public void sharpen() {
		buffer = new DrawingImage();
		buffer.setPixels(this.getImage());
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
//				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				buffer.getPixel(x, y, rgb);
				// Convert to HSB.
//				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
				setSurroundingPixels(x, y);
				for(int color = 0; color < 3; color++){
					double sharpval = (-rgbpos1[color] - rgbpos3[color] - rgbpos5[color] - rgbpos7[color] 
							+ (6*rgbpos4[color]))/2;
					if(sharpval < 0){
						sharpval = 0;
					}
					if(sharpval > 255){
						sharpval = 255;
					}
					rgb[color] = (int) sharpval;
				}
				
				// Convert back to RGB.
//				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
//				rgb[0] = c.getRed();
//				rgb[1] = c.getGreen();
//				rgb[2] = c.getBlue();
				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
		this.notifyObservers();
	}

	@Override
	public void uniformBlur() {
		buffer = new DrawingImage();
		buffer.setPixels(this.getImage());
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
//				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				buffer.getPixel(x, y, rgb);
				// Convert to HSB.
//				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
				setSurroundingPixels(x, y);
				for(int color = 0; color < 3; color++){
					int average = (rgbpos0[color] + rgbpos1[color] + rgbpos2[color]
							+ rgbpos3[color] + rgbpos4[color] + rgbpos5[color]
							+ rgbpos6[color] + rgbpos7[color] + rgbpos8[color])/9;
					rgb[color] = average;
				}
										
				// Convert back to RGB.
//				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
//				rgb[0] = c.getRed();
//				rgb[1] = c.getGreen();
//				rgb[2] = c.getBlue();
				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
		this.notifyObservers();
	}

	@Override
	public void medianBlur() {
		buffer = new DrawingImage();
		buffer.setPixels(this.getImage());
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				buffer.getPixel(x, y, rgb);
				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
				setSurroundingPixels(x, y);
				for(int color = 0; color < 3; color++){
					int[] colors = new int[9];
					colors[0] = rgbpos0[color];
					colors[1] = rgbpos1[color];
					colors[2] = rgbpos2[color];
					colors[3] = rgbpos3[color];
					colors[4] = rgbpos4[color];
					colors[5] = rgbpos5[color];
					colors[6] = rgbpos6[color];
					colors[7] = rgbpos7[color];
					colors[8] = rgbpos8[color];
					
					Arrays.sort(colors);
					
					rgb[color] = colors[4];
				}
				
				// Convert back to RGB.
//				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
//				rgb[0] = c.getRed();
//				rgb[1] = c.getGreen();
//				rgb[2] = c.getBlue();
				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
		this.notifyObservers();
	}

	@Override
	public void grayscale() {
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				getPixel(x, y, rgb);
				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
				hsb[1] = 0;
				
				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = c.getRed();
				rgb[1] = c.getGreen();
				rgb[2] = c.getBlue();
				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
		this.notifyObservers();
	}

	@Override
	public void contrast(int amount) {
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				getPixel(x, y, rgb);
				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
//				float[] hsb = getPixelHSB(x, y);
				float newBrightness = (float) ((Math.pow((double) ((amount + 100f) / 100f), 4) * (hsb[2] - .5f)) + .5f);
				hsb[2] = newBrightness;
				if (hsb[2] > 1.0)
				    hsb[2] = 1.0f;
				else if (hsb[2] < 0.0)
				    hsb[2] = 0.0f;
//				setPixelHSB(x, y, hsb);
				
				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = c.getRed();
				rgb[1] = c.getGreen();
				rgb[2] = c.getBlue();
				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
		this.notifyObservers();
	}

	@Override
	public void brightness(int amount) {
		for (int x = 0; x < this.getWidth(); x++){
			for (int y = 0; y < this.getHeight(); y++){
				// Preallocate the arrays.
				int[] rgb = new int[3];
				float[] hsb = new float[3];
				// Do the following for each pixel:
				// Get the color from the image.
				getPixel(x, y, rgb);
				// Convert to HSB.
				Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsb);
				// Do whatever operation you’re supposed to do...
				double brightness = amount/100f;
				hsb[2] += brightness;
				
				if (hsb[2] > 1.0)
				    hsb[2] = 1.0f;
				else if (hsb[2] < 0.0)
				    hsb[2] = 0.0f;
				
				// Convert back to RGB.
				Color c = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
				rgb[0] = c.getRed();
				rgb[1] = c.getGreen();
				rgb[2] = c.getBlue();
				// Set the pixel.
				setPixel(x, y, rgb);
			}
		}
		this.notifyObservers();
	}
	
	private void setSurroundingPixels(int x, int y){
		if(x == 0){
			x = 1;
		}
		if(y == 0){
			y = 1;
		}
		if(x == buffer.getWidth()-1){
			x = buffer.getWidth()-2;
		}
		if(y == buffer.getHeight()-1){
			y = buffer.getHeight()-2;
		}
		
//		int[] rgb = new int[3];
		
		rgbpos0 = buffer.getPixel(x-1, y-1, rgbpos0);
		Color.RGBtoHSB(rgbpos0[0], rgbpos0[1], rgbpos0[2], hsbpos0);
		
		rgbpos1 = buffer.getPixel(x, y-1, rgbpos1);
		Color.RGBtoHSB(rgbpos1[0], rgbpos1[1], rgbpos1[2], hsbpos1);
		
		rgbpos2 = buffer.getPixel(x+1, y-1, rgbpos2);
		Color.RGBtoHSB(rgbpos2[0], rgbpos2[1], rgbpos2[2], hsbpos2);

		rgbpos3 = buffer.getPixel(x-1, y, rgbpos3);
		Color.RGBtoHSB(rgbpos3[0], rgbpos3[1], rgbpos3[2], hsbpos3);

		rgbpos4 = buffer.getPixel(x, y, rgbpos4);
		Color.RGBtoHSB(rgbpos4[0], rgbpos4[1], rgbpos4[2], hsbpos4);

		rgbpos5 = buffer.getPixel(x+1, y, rgbpos5);
		Color.RGBtoHSB(rgbpos5[0], rgbpos5[1], rgbpos5[2], hsbpos5);
		
		rgbpos6 = buffer.getPixel(x-1, y+1, rgbpos6);
		Color.RGBtoHSB(rgbpos6[0], rgbpos6[1], rgbpos6[2], hsbpos6);

		rgbpos7 = buffer.getPixel(x, y+1, rgbpos7);
		Color.RGBtoHSB(rgbpos7[0], rgbpos7[1], rgbpos7[2], hsbpos7);

		rgbpos8 = buffer.getPixel(x+1, y+1, rgbpos8);
		Color.RGBtoHSB(rgbpos8[0], rgbpos8[1], rgbpos8[2], hsbpos8);


	}
	
	public void toggleDisplayImage(){
		drawImage = !drawImage;
		this.notifyObservers();
	}

}
