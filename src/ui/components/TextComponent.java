package ui.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.component.Text;

@SuppressWarnings("serial")
public class TextComponent extends MirrorComponent<Text> implements
		ActionListener {

	private String text;
	private Timer t;

	public TextComponent(Text component) {
		super(component);
		t = new Timer(component.getDelay(), this);
		t.start();
		actionPerformed(null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		text = component.getNext();
		t.setDelay(component.getDelay());
	}

	@Override
	protected void updateComponent() {
		if (t != null) {
			t.setDelay(component.getDelay());
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(BACKGROUND);
		g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);

		String text = this.text;
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
