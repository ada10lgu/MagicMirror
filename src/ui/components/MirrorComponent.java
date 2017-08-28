package ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.MirrorModel;
import model.component.Clock;
import model.component.Component;
import model.component.House;
import model.component.Text;

@SuppressWarnings("serial")
public abstract class MirrorComponent<E extends Component> extends JPanel {
	public static final Color BACKGROUND = Color.BLACK;
	public static final Color BORDER = Color.LIGHT_GRAY;
	public static final Color TEXT = Color.WHITE;

	protected E component;
	protected MirrorModel model;

	public MirrorComponent(E component, MirrorModel model) {
		this.component = component;
		this.model = model;
		this.update();
	}

	public final void update() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		setBounds(component.getBounds(width, height));

		updateComponent();
	}

	@Override
	public final void paint(Graphics g) {
		if (model.isDebug()) {
			g.setColor(BORDER);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(BACKGROUND);
			g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
		} else {
			g.setColor(BACKGROUND);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		paintComponent(g);
		if (model.isDebug()) {
			g.setColor(BORDER);
			g.drawLine(0, 0, getWidth(), getHeight());
			g.drawLine(0, getHeight(), getWidth(), 0);
			g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
			g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
		}

	}

	protected abstract void paintComponent(Graphics g);

	protected abstract void updateComponent();

	public static MirrorComponent<?> create(Component component,
			MirrorModel model) {
		MirrorComponent<?> comp;
		switch (component.getType()) {
		case "clock":
			comp = new ClockComponent((Clock) component, model);
			break;
		case "text":
		case "nameday":
			comp = new TextComponent((Text) component, model);
			break;
		case "house":
			comp = new HouseComponent((House) component, model);
			break;
		default:
			comp = new UnknownComponent(component, model);
		}

		return comp;
	}
}
