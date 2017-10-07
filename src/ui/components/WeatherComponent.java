package ui.components;

import java.awt.Graphics;

import model.MirrorModel;
import model.component.Weather;

@SuppressWarnings("serial")
public class WeatherComponent extends MirrorComponent<Weather> {

	public WeatherComponent(Weather component, MirrorModel model) {
		super(component, model);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(TEXT);
		g.setFont(component.getFont());
		if (component.hasData()) {

			String temp = component.getTemp()+"°C";

			g.drawString(temp, 0, 80);
		}
	}

	@Override
	protected void updateComponent() {

	}

}
