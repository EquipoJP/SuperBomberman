package logic.characters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import utils.Animation;

public class WhiteBomberman {

	/* Info to get Sprites */
	private HashMap<String, BufferedImage[]> sprites;
	private final String[] nameArray = { "idle", "walkdown", "walkside", "walkup", "victory" };

	private final String IDLESPR = "../resources/sprites/whiteBomber/idle";
	private final String WALKDOWNSPR = "../resources/sprites/whiteBomber/walkdown";
	private final String WALKSIDESPR = "../resources/sprites/whiteBomber/walkside";
	private final String WALKUPSPR = "../resources/sprites/whiteBomber/walkup";
	private final String VICTORYSPR = "../resources/sprites/whiteBomber/victory";
	private final String[] filesArray = { IDLESPR, WALKDOWNSPR, WALKSIDESPR, WALKUPSPR, VICTORYSPR };

	public WhiteBomberman() {
		try {
			// Get transparency color
			BufferedImage trans = ImageIO.read(new File(IDLESPR));
			int transparency = trans.getRGB(0, 0);

			// Set sprites
			for (int i = 0; i < filesArray.length; i++) {
				// Get sprite info
				File f = new File(filesArray[i] + ".info");
				Scanner s = new Scanner(f);
				int width = s.nextInt();
				s.nextLine();
				int height = s.nextInt();
				s.nextLine();
				int frames = s.nextInt();
				s.close();

				BufferedImage sheet = ImageIO.read(new File(filesArray[i] + ".png"));
				BufferedImage[] sprite = Animation.getSpritesFromImage(sheet, frames, width, height, transparency);
				sprites.put(nameArray[i], sprite);
			}
		} catch (FileNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

}
