/**
 * Class representing a fade-in/fade-out effect
 */
package graphics.effects;

import java.awt.Color;
import java.awt.Graphics;

import graphics.rooms.Room;
import logic.Global;
import logic.Input.KEY;
import logic.Objeto;

/**
 * @author Patricia Lazaro Tello (554309)
 * @author Jaime Ruiz-Borau Vizarraga (546751)
 */
public class Fade extends Objeto {

	private float alpha = 0.0f;
	private boolean sum = true;

	private final float MOD = 0.02f;
	private final float MIN = 0.0f;
	private final float MAX = 1.0f;

	/**
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param r
	 *            room
	 * @param isFadeOut
	 *            true if is fade-out, false if is fade-in
	 */
	public Fade(int x, int y, Room r, boolean isFadeOut) {
		super(x, y, 0, r);
		depth = Global.EFFECTS_DEPTH;
		sum = isFadeOut;
		if (sum) {
			alpha = MIN;
		} else {
			alpha = MAX;
		}
	}

	@Override
	public void render(Graphics g) {
		// Paints black curtain
		Color transparent = new Color(0, 0, 0, alpha);
		g.setColor(transparent);
		g.fillRect(0, 0, myRoom.width, myRoom.height);
		g.setColor(Color.black);
	}

	@Override
	public void customStep(KEY key, KEY direction) {
		if (sum) {
			if (alpha < MAX) {
				alpha = alpha + MOD;
			}
			if (alpha > MAX) {
				alpha = MAX;
			}
			// Destroy if alpha = MAX
			if (alpha == MAX) {
				destroy();
			}
		} else {
			if (alpha > MIN) {
				alpha = alpha - MOD;
			}
			if (alpha < MIN) {
				alpha = MIN;
			}
			// Destroy if alpha = MIN
			if (alpha == MIN) {
				destroy();
			}
		}
	}

	/**
	 * @return if is fade-out
	 */
	public boolean isFadeOut() {
		return sum;
	}
}
