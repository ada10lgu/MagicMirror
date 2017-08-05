package ui.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import ui.MirrorComponent;

@SuppressWarnings("serial")
public class TextComponent extends MirrorComponent {

	private Font textFont;
	private String text = "";
	private int i = 0;

	public TextComponent(JSONObject extra) {
		super(extra);

		textFont = extract(extra.getJSONObject("font"));

		start(extra.getLong("delay") * 1000);
		work();
	}

	@Override
	protected void work() {
		JSONArray array = extra.getJSONArray("text");
		if (extra.getBoolean("random")) {
			text = array.get(new Random().nextInt(array.length())).toString();
		} else {
			i %= array.length();
			text = array.get(i++).toString();
		}

		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(TEXT);

		g.setFont(textFont);
		FontMetrics fm = g.getFontMetrics();

		int x = getWidth() / 2 - fm.stringWidth(text) / 2;
		int y = getHeight() / 2 + fm.getAscent() / 2;

		g.drawString(text, x, y);

	}
}
