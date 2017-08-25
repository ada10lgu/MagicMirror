package ui.components;

import java.awt.FontMetrics;
import java.awt.Graphics;

import model.MirrorModel;
import model.component.Text;

@SuppressWarnings("serial")
public class TextComponent extends MirrorComponent<Text> {

	public TextComponent(Text component, MirrorModel model) {
		super(component, model);

	}

	@Override
	protected void updateComponent() {

	}

	@Override
	public void paintComponent(Graphics g) {
		String text = component.getText();
		g.setFont(component.getFont());
		FontMetrics fm = g.getFontMetrics();
		g.setColor(TEXT);
		int x = getWidth() / 2 - fm.stringWidth(text) / 2;
		switch (component.getAllign()) {
		case "left":
			x = 0;
			break;
		case "right":
			x = getWidth() - fm.stringWidth(text);
			break;
		}
		int y = getHeight() / 2 + (fm.getHeight() - fm.getDescent()) / 2;
		g.drawString(text, x, y);
	}

}
