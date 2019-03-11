package mirror.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.json.JSONObject;

import mirror.gui.components.Component;
import mirror.model.Config;
import mirror.model.MirrorModel;
import mirror.model.resource.Resource;

@SuppressWarnings("serial")
public class BasePane extends JPanel implements ActionListener {

	private JPanel panel;
	private Config config;
	private MirrorModel model;
	private ImageLoader imageLoader;

	public BasePane(MirrorModel model, Config config, ImageLoader imageLoader) {
		this.model = model;
		this.config = config;
		this.imageLoader = imageLoader;

		setBackground(Component.BACKGROUND);
		setLayout(null);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Component.BACKGROUND);
		add(panel);
		loadComponents();

		Timer t = new Timer(30, this);
		t.start();
	}

	@Override
	public void repaint() {
		if (getWidth() > 0 && getHeight() > 0) {
			JSONObject bounds = config.getGUISettings().getJSONObject("bounds");
			int x = bounds.getInt("l");
			int y = bounds.getInt("t");
			int w = getWidth() - bounds.getInt("r") - x;
			int h = getHeight() - bounds.getInt("b") - y;
			Rectangle rect = new Rectangle(x, y, w, h);
			panel.setBounds(rect);
		}
		super.repaint();
	}

	private void loadComponents() {
		for (Resource r : model.getResources()) {
			Component<?> comp = Component.create(r, config, imageLoader);
			comp.setBounds(0, 0, 10, 10);
			panel.add(comp);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		updateUI();
		repaint();
	}

}
