package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import model.MirrorModel;
import model.component.Component;
import ui.components.MirrorComponent;

@SuppressWarnings("serial")
public class BasePane extends JPanel implements Observer, ActionListener {

	private MirrorModel model;

	public BasePane(MirrorModel model) {
		this.model = model;
		setBackground(Color.BLACK);
		setLayout(null);

		update(null, null);

		model.addObserver(this);

		Timer t = new Timer(30, this);
		t.start();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		removeAll();
		for (Component component : model.getComponents()) {
			add(MirrorComponent.create(component,model));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
