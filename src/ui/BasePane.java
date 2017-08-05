package ui;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BasePane extends JPanel {
	public BasePane(List<MirrorComponent> components) {
		setBackground(Color.BLACK);
		setLayout(null);
		for (MirrorComponent component : components) {
			add(component);
		}
	}
}
