package ui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

import model.component.Clock;
import model.component.Component;


@SuppressWarnings("serial")
public abstract class MirrorComponent<E extends Component> extends JPanel {
	public static final Color BACKGROUND = Color.BLACK;
	public static final Color TEXT = Color.WHITE;

	protected E component;

	public MirrorComponent(E component) {
		this.component = component;
		this.update();
	}

	public final void update() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		setBounds(component.getBounds(width, height));

		updateComponent();
	}

	protected abstract void updateComponent();

	public static MirrorComponent<?> create(Component component) {
		MirrorComponent<?> comp;
		switch (component.getType()) {
		case "clock":
			comp = new ClockComponent((Clock) component);
			break;
		default:
			comp = new UnknownComponent(component);
		}

		return comp;
	}
}
