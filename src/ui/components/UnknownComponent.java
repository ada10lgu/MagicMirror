package ui.components;

import java.awt.Color;

import org.json.JSONObject;

import ui.MirrorComponent;

@SuppressWarnings("serial")
public class UnknownComponent extends MirrorComponent {
	public UnknownComponent(JSONObject extra) {
		super(extra);
		setBackground(Color.RED);
	}
}
