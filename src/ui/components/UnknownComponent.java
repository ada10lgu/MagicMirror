package ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import model.component.Component;

@SuppressWarnings("serial")
public class UnknownComponent extends MirrorComponent<Component> {

	private Random r;

	public UnknownComponent(Component component) {
		super(component);
		r = new Random();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(new Color(255, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		int diff = r.nextInt(30) + 10;
		g.setColor(new Color(102 + diff, diff, diff));
		g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
	}

	@Override
	protected void updateComponent() {

	}
}
