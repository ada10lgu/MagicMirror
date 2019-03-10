package mirror.gui;

import java.awt.Color;

import javax.swing.JPanel;

import mirror.gui.components.Component;
import mirror.model.Config;
import mirror.model.MirrorModel;
import mirror.model.resource.Resource;

@SuppressWarnings("serial")
public class BasePane extends JPanel {

	public BasePane(MirrorModel model, Config config) {
		setBackground(Color.BLACK);
		setLayout(null);
		int x = 0;
		for (Resource r : model.getResources()){
			Component<?> comp = Component.create(r, config);
			comp.setBounds(x, 0, 100, 100);
			x+=100;
			add(comp);
		}
	}
}
