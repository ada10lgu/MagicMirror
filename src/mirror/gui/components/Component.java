package mirror.gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import mirror.model.Config;
import mirror.model.resource.Resource;

@SuppressWarnings("serial")
public abstract class Component<E extends Resource> extends JComponent {

	protected static final Color BACKGROUND = Color.BLACK;
	protected static final Color TEXT = Color.WHITE;

	protected E resource;
	protected Config config;

	public Component(E resource, Config config) {
		this.resource = resource;
		this.config = config;
	}

	public static Component<?> create(Resource r, Config config) {

		switch (r.getType()) {
		default:
			System.out.printf("Missing graphics for type %s\n", r.getType());
			return new UnknownComponent(r, config);
		}

	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		paintBackground(g2);
		paintBody(g2);
		paintDebug(g2);
	}

	private void paintBackground(Graphics2D g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	protected abstract void paintBody(Graphics2D g);

	private void paintDebug(Graphics2D g) {

	}

}
