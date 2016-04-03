package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import logic.Sprite;
import logic.collisions.Point2D;
import main.Initialization;

public class PaintDigitsService {

	private static String charAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ?!";

	private static Sprite alphabet = Initialization.getSpriteFromMenu(Initialization.MENUS.ALPHABET.toString());
	private static Sprite digits = Initialization.getSpriteFromMenu(Initialization.MENUS.DIGITS.toString());
	private static Sprite newRecord = Initialization.getSpriteFromMenu(Initialization.MENUS.NEW.toString());

	public static void paintText(String text, Point2D initial_position, Graphics g) {
		int modX = alphabet.getWidth();

		for (int i = 0; i < text.length(); i++) {
			char c = text.toUpperCase().charAt(i);
			try {
				g.drawImage(alphabet.getSubsprites()[getCharInt(c)], initial_position.getX() + modX * i,
						initial_position.getY(), null);
			} catch (ArrayIndexOutOfBoundsException e) {
				// Do nothing
			}
		}

	}

	public static void paintTextColor(String text, Point2D initial_position, Graphics g, int R, int G, int B) {
		int modX = alphabet.getWidth();

		for (int i = 0; i < text.length(); i++) {
			char c = text.toUpperCase().charAt(i);
			try {
				BufferedImage buf = alphabet.getSubsprites()[getCharInt(c)];
				buf = changeColor(buf, R, G, B);
				g.drawImage(buf, initial_position.getX() + modX * i, initial_position.getY(), null);
			} catch (ArrayIndexOutOfBoundsException e) {
				// Do nothing
			}
		}

	}
	
	public static void paintTextColor(String text, Point2D initial_position, Graphics g, int RGB) {
		int modX = alphabet.getWidth();

		for (int i = 0; i < text.length(); i++) {
			char c = text.toUpperCase().charAt(i);
			try {
				BufferedImage buf = alphabet.getSubsprites()[getCharInt(c)];
				buf = changeColor(buf, RGB);
				g.drawImage(buf, initial_position.getX() + modX * i, initial_position.getY(), null);
			} catch (ArrayIndexOutOfBoundsException e) {
				// Do nothing
			}
		}

	}

	public static void paint(String number, Point2D initial_position, Graphics g) {
		int modX = digits.getWidth();

		if (number.startsWith("NEW ")) {
			number = number.substring(4);
			g.drawImage(newRecord.getSubsprites()[0], initial_position.getX() - modX * 4, initial_position.getY(),
					null);
		}

		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			int j = 10;
			if (Character.isDigit(c)) {
				j = Character.digit(number.charAt(i), 10);
				g.drawImage(digits.getSubsprites()[j], initial_position.getX() + modX * i, initial_position.getY(),
						null);
			} else if (c == ' ') {
				;
			} else if (c == '.') {
				g.drawImage(digits.getSubsprites()[j], initial_position.getX() + modX * i, initial_position.getY(),
						null);
			} else if (c == ':') {
				g.drawImage(digits.getSubsprites()[j + 1], initial_position.getX() + modX * i, initial_position.getY(),
						null);
			}
		}
	}

	private static int getCharInt(char c) {
		int returned = -1;
		for (int i = 0; i < charAlphabet.length() && returned == -1; i++) {
			if (c == charAlphabet.charAt(i)) {
				returned = i;
			}
		}
		return returned;
	}

	private static BufferedImage changeColor(BufferedImage input, int R, int G, int B) {
		Color old = new Color(255, 255, 255);
		Color NEW = new Color(R, G, B);
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
	
	private static BufferedImage changeColor(BufferedImage input, int RGB) {
		Color old = new Color(255, 255, 255);
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

}
