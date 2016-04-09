/**
 * Class for painting characters and digits on screen
 */
package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import logic.Sprite;
import logic.collisions.Point2D;
import main.Initialization;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class PaintService {

	private static String charAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
	private static String charDigits = "0123456789.:";
	private static Sprite alphabet = Initialization
			.getSpriteFromMenu(Initialization.MENUS.ALPHABET.toString());
	private static Sprite digits = Initialization
			.getSpriteFromMenu(Initialization.MENUS.DIGITS.toString());
	private static Sprite newRecord = Initialization
			.getSpriteFromMenu(Initialization.MENUS.NEW.toString());

	public static enum TYPE {
		ALPHABET, DIGIT
	};

	public static Color DIGITS_ORANGE = new Color(250, 81, 0);
	public static Color ALPHABET_WHITE = new Color(255, 255, 255);

	/**
	 * Method for painting white text on the screen at initial_position position
	 * 
	 * @param text
	 *            text to paint on screen
	 * @param initial_position
	 *            initial position in which start to paint
	 * @param g
	 *            graphic to paint in
	 */
	public static void paintText(String text, Point2D initial_position,
			Graphics g) {
		int modX = alphabet.getWidth();

		for (int i = 0; i < text.length(); i++) {
			char c = text.toUpperCase().charAt(i);
			try {
				g.drawImage(alphabet.getSubsprites()[getCharInt(c)],
						initial_position.getX() + modX * i,
						initial_position.getY(), null);
			} catch (ArrayIndexOutOfBoundsException e) {
				// Check if it's digit
				if (isDigit(text.charAt(i))) {
					String temp = text.charAt(i) + "";
					Point2D tempPoint = new Point2D(initial_position.getX()
							+ modX * i, initial_position.getY());
					paintDigitsColor(temp, tempPoint, g,
							ALPHABET_WHITE.getRGB());
				} else {
					// Do nothing
				}
			}
		}

	}

	/**
	 * Method for painting colored text on the screen at initial_position
	 * position
	 * 
	 * @param text
	 *            text to paint on screen
	 * @param initial_position
	 *            initial position in which start to paint
	 * @param g
	 *            graphic to paint in
	 * @param RGB
	 *            color to paint the text with
	 */
	public static void paintTextColor(String text, Point2D initial_position,
			Graphics g, int RGB) {
		int modX = alphabet.getWidth();

		for (int i = 0; i < text.length(); i++) {
			char c = text.toUpperCase().charAt(i);
			try {
				BufferedImage buf = changeColor(
						alphabet.getSubsprites()[getCharInt(c)], RGB,
						TYPE.ALPHABET);
				g.drawImage(buf, initial_position.getX() + modX * i,
						initial_position.getY(), null);
			} catch (ArrayIndexOutOfBoundsException e) {
				// Check if it's digit
				if (isDigit(text.charAt(i))) {
					String temp = text.charAt(i) + "";
					Point2D tempPoint = new Point2D(initial_position.getX()
							+ modX * i, initial_position.getY());
					paintDigitsColor(temp, tempPoint, g, RGB);
				} else {
					// Do nothing
				}
			}
		}
	}

	/**
	 * Method for painting colored text on the screen at initial_position
	 * position
	 * 
	 * @param text
	 *            text to paint on screen
	 * @param initial_position
	 *            initial position in which start to paint
	 * @param g
	 *            graphic to paint in
	 * @param R
	 *            red color
	 * @param G
	 *            green color
	 * @param B
	 *            blue color
	 */
	public static void paintTextColor(String text, Point2D initial_position,
			Graphics g, int R, int G, int B) {
		Color c = new Color(R, G, B);
		paintTextColor(text, initial_position, g, c.getRGB());
	}

	/**
	 * Method to paint digits, records and ranking
	 * 
	 * @param number
	 *            number to paint
	 * @param initial_position
	 *            initial position in which start to paint
	 * @param g
	 *            graphic to paint in
	 */
	public static void paintDigits(String number, Point2D initial_position,
			Graphics g) {
		int modX = digits.getWidth();

		if (number.startsWith("NEW ")) {
			number = number.substring(4);
			g.drawImage(newRecord.getSubsprites()[0], initial_position.getX()
					- modX * 4, initial_position.getY(), null);
		}

		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			int j = 10;
			if (Character.isDigit(c)) {
				j = Character.digit(number.charAt(i), 10);
				g.drawImage(digits.getSubsprites()[j], initial_position.getX()
						+ modX * i, initial_position.getY(), null);
			} else if (c == ' ') {
				;
			} else if (c == '.') {
				g.drawImage(digits.getSubsprites()[j], initial_position.getX()
						+ modX * i, initial_position.getY(), null);
			} else if (c == ':') {
				g.drawImage(digits.getSubsprites()[j + 1],
						initial_position.getX() + modX * i,
						initial_position.getY(), null);
			}
		}
	}

	/**
	 * Method to paint colored digits on screen
	 * 
	 * @param number
	 *            text to paint on screen
	 * @param initial_position
	 *            initial position in which start to paint
	 * @param g
	 *            graphic to paint in
	 * @param RGB
	 *            color
	 */
	public static void paintDigitsColor(String number,
			Point2D initial_position, Graphics g, int RGB) {
		int modX = digits.getWidth();

		if (number.startsWith("NEW ")) {
			number = number.substring(4);
			g.drawImage(newRecord.getSubsprites()[0], initial_position.getX()
					- modX * 4, initial_position.getY(), null);
		}

		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			int j = 10;
			if (Character.isDigit(c)) {
				j = Character.digit(number.charAt(i), 10);
				BufferedImage buf = changeColor(digits.getSubsprites()[j], RGB,
						TYPE.DIGIT);
				g.drawImage(buf, initial_position.getX() + modX * i,
						initial_position.getY(), null);
			} else if (c == ' ') {
				;
			} else if (c == '.') {
				BufferedImage buf = changeColor(digits.getSubsprites()[j], RGB,
						TYPE.DIGIT);
				g.drawImage(buf, initial_position.getX() + modX * i,
						initial_position.getY(), null);
			} else if (c == ':') {
				BufferedImage buf = changeColor(digits.getSubsprites()[j + 1],
						RGB, TYPE.DIGIT);
				g.drawImage(buf, initial_position.getX() + modX * i,
						initial_position.getY(), null);
			}
		}
	}

	/**
	 * Method for painting colored text on the screen at initial_position
	 * position
	 * 
	 * @param number
	 *            text to paint on screen
	 * @param initial_position
	 *            initial position in which start to paint
	 * @param g
	 *            graphic to paint in
	 * @param R
	 *            red color
	 * @param G
	 *            green color
	 * @param B
	 *            blue color
	 */
	public static void paintDigitsColor(String number,
			Point2D initial_position, Graphics g, int R, int G, int B) {
		Color c = new Color(R, G, B);
		paintDigitsColor(number, initial_position, g, c.getRGB());
	}

	/**
	 * Method that returns the integer codification of a character
	 * 
	 * @param c
	 *            character to code
	 * @return coded integer
	 */
	private static int getCharInt(char c) {
		int returned = -1;
		for (int i = 0; i < charAlphabet.length() && returned == -1; i++) {
			if (c == charAlphabet.charAt(i)) {
				returned = i;
			}
		}
		return returned;
	}

	/**
	 * Method to change color of the image
	 * 
	 * @param input
	 *            image
	 * @param RGB
	 *            final color
	 * @param t
	 *            type of image (digit or alphabet)
	 * @return a final-colored image
	 */
	private static BufferedImage changeColor(BufferedImage input, int RGB,
			TYPE t) {
		Color old = null;
		if (t == TYPE.ALPHABET) {
			old = ALPHABET_WHITE;
		} else if (t == TYPE.DIGIT) {
			old = DIGITS_ORANGE;
		} else {
			old = new Color(0, 0, 0);
		}
		Color NEW = new Color(RGB);
		for (int i = 0; i < input.getWidth(); i++) {
			for (int j = 0; j < input.getHeight(); j++) {
				Color actual = new Color(input.getRGB(i, j));
				if (actual.getRGB() == old.getRGB()) {
					input.setRGB(i, j, NEW.getRGB());
				}
			}
		}
		return input;
	}

	/**
	 * Method to check if a character is a digit
	 * 
	 * @param c
	 *            character
	 * @return true if c is digit, false otherwise
	 */
	private static boolean isDigit(char c) {
		boolean found = false;
		for (int i = 0; i < charDigits.length() && !found; i++) {
			if (c == charDigits.charAt(i)) {
				found = true;
			}
		}
		return found;
	}
}
