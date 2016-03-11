package utils;

import java.awt.Graphics;

import logic.Sprite;
import logic.collisions.Point2D;
import main.Initialization;

public class PaintDigitsService {

	private static Sprite digits = Initialization.getSpriteFromMenu(Initialization.BUTTONS.DIGITS.toString());

	public static void paint(String number, Point2D initial_position, Graphics g) {
		int modX = digits.getWidth();

		for (int i = 0; i < number.length(); i++) {
			char c = number.charAt(i);
			int j = 10;
			if (Character.isDigit(c)){
				j = Character.digit(number.charAt(i), 10);
				g.drawImage(digits.getSubsprites()[j], initial_position.getX() + modX * i, initial_position.getY(), null);
			}
			else if(c == ' '){
				;
			}
			else if(c == '.'){
				g.drawImage(digits.getSubsprites()[j], initial_position.getX() + modX * i, initial_position.getY(), null);
			}
		}
	}

}
