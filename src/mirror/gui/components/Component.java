package mirror.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import mirror.model.Config;
import mirror.model.resource.Clock;
import mirror.model.resource.Resource;

@SuppressWarnings("serial")
public abstract class Component<E extends Resource> extends JComponent {

	public static final Color BACKGROUND = Color.BLACK;
	public static final Color TEXT = Color.WHITE;
	public static final Color DEBUG = Color.RED;

	protected E resource;
	protected Config config;

	public Component(E resource, Config config) {
		this.resource = resource;
		this.config = config;
	}

	public static Component<?> create(Resource r, Config config) {

		switch (r.getType()) {
		case "clock":
			Clock c = (Clock) r;
			return new ClockComponent(c, config);
		default:
			System.out.printf("Missing graphics for type %s\n", r.getType());
			return new UnknownComponent(r, config);
		}
	}

	private void updateBounds() {
		setBounds(resource.getBounds());
	}

	@Override
	public void paint(Graphics g) {
		updateBounds();
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
		if (!config.isDebug())
			return;
		g.setColor(DEBUG);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.drawLine(0, getHeight() / 2, 10, getHeight() / 2);
		g.drawLine(getWidth() - 1, getHeight() / 2, getWidth() - 11, getHeight() / 2);
		g.drawLine(getWidth() / 2, 0, getWidth() / 2, 10);
		g.drawLine(getWidth() / 2, getHeight() - 1, getWidth() / 2, getHeight() - 11);

		g.drawLine(getWidth() / 2 - 10, getHeight() / 2, getWidth() / 2 + 10, getHeight() / 2);
		g.drawLine(getWidth() / 2, getHeight() / 2 - 10, getWidth() / 2, getHeight() / 2 + 10);
	}

	protected Font getFont(int size) {
		String fontFamily = config.getGUISettings().getString("font");
		Font font = new Font(fontFamily, Font.PLAIN, size);
		return font;
	}

}
