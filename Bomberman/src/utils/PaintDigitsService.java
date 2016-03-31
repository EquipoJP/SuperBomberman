package utils;

import java.awt.Graphics;

import logic.Sprite;
import logic.collisions.Point2D;
import main.Initialization;

public class PaintDigitsService {

	private static Sprite digits = Initialization.getSpriteFromMenu(Initialization.MENUS.DIGITS.toString());
	private static Sprite newRecord = Initialization.getSpriteFromMenu(Initialization.MENUS.NEW.toString());

	public static void paint(String number, Point2D initial_position, Graphics g) {
		int modX = digits.getWidth();
		
		if(number.startsWith("NEW ")){
			number = number.substring(4);
			g.drawImage(newRecord.getSubsprites()[0], initial_position.getX() - modX * 4, initial_position.getY(), null);
		}

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
			else if(c == ':'){
				g.drawImage(digits.getSubsprites()[j+1], initial_position.getX() + modX * i, initial_position.getY(), null);
			}
		}
	}

}
