package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Animation;

public class Sprite {

	private BufferedImage[] subsprites;
	private int subimages;
	private int centerX;
	private int centerY;
	private int width;
	private int height;
	
	public Sprite(File sheet, int sI, int cX, int cY, int w, int h){
		BufferedImage img;
		try {
			img = ImageIO.read(sheet);
			subimages = sI;
			subsprites = Animation.getSpritesFromImage(img, sI, w, h);
			centerX = cX;
			centerY = cY;
			width = w;
			height = h;			
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Sprite(File sheet, int sI, int cX, int cY, int w, int h, int transparency){
		BufferedImage img;
		try {
			img = ImageIO.read(sheet);
			subimages = sI;
			subsprites = Animation.getSpritesFromImage(img, sI, w, h, transparency);
			centerX = cX;
			centerY = cY;
			width = w;
			height = h;			
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BufferedImage[] getSubsprites() {
		return subsprites;
	}

	public void setSubsprites(BufferedImage[] subsprites) {
		this.subsprites = subsprites;
	}

	public int getSubimages() {
		return subimages;
	}

	public void setSubimages(int subimages) {
		this.subimages = subimages;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
