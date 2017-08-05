package ui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.json.JSONObject;

import ui.MirrorComponent;

@SuppressWarnings("serial")
public class ExitButton extends MirrorComponent implements ActionListener {
	public ExitButton(JSONObject extra) {
		super(extra);
		setLayout(new BorderLayout());
		JButton b = new JButton("exit");
		b.addActionListener(this);
		add(b);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);
		
	}
}
