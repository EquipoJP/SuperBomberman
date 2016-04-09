/**
 * Class representing an sprite
 */
package logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Animation;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Sprite {

	private BufferedImage[] subsprites;
	private int subimages;

	private int centerX;
	private int centerY;

	private int width;
	private int height;

	/**
	 * @param sheet
	 *            sprisheet
	 * @param sI
	 *            subimages
	 * @param cX
	 *            center (x)
	 * @param cY
	 *            center (y)
	 * @param w
	 *            width of a subimage
	 * @param h
	 *            height of a subimage
	 */
	public Sprite(File sheet, int sI, int cX, int cY, int w, int h) {
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
			e.printStackTrace();
		}
	}

	/**
	 * @param sheet
	 *            sprisheet
	 * @param sI
	 *            subimages
	 * @param cX
	 *            center (x)
	 * @param cY
	 *            center (y)
	 * @param w
	 *            width of a subimage
	 * @param h
	 *            height of a subimage
	 * @param transparency
	 *            transparency to-be-color
	 */
	public Sprite(File sheet, int sI, int cX, int cY, int w, int h,
			int transparency) {
		BufferedImage img;
		try {
			img = ImageIO.read(sheet);
			subimages = sI;
			subsprites = Animation.getSpritesFromImage(img, sI, w, h,
					transparency);
			centerX = cX;
			centerY = cY;
			width = w;
			height = h;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param sheet
	 *            spritesheet
	 * @param subImages
	 *            subimages
	 * @param width
	 *            width
	 * @param height
	 *            height
	 */
	public Sprite(BufferedImage[] sheet, int subImages, int width, int height) {
		this.subsprites = sheet;
		this.subimages = subImages;
		this.width = width;
		this.height = height;
		this.centerX = width / 2;
		this.centerY = height / 2;
	}

	/**
	 * @return subimages
	 */
	public BufferedImage[] getSubsprites() {
		return subsprites;
	}

	/**
	 * @param subsprites
	 *            subimages
	 */
	public void setSubsprites(BufferedImage[] subsprites) {
		this.subsprites = subsprites;
	}

	/**
	 * @return number of subimages
	 */
	public int getSubimages() {
		return subimages;
	}

	/**
	 * @param subimages
	 *            number of subimages
	 */
	public void setSubimages(int subimages) {
		this.subimages = subimages;
	}

	/**
	 * @return center (x)
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * @param centerX
	 *            center (x)
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	/**
	 * @return center (y)
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * @param centerY
	 *            center (y)
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
}