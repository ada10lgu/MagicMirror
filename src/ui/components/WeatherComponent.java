package ui.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import model.MirrorModel;
import model.component.Weather;

@SuppressWarnings("serial")
public class WeatherComponent extends MirrorComponent<Weather> {

	private HashMap<String, BufferedImage> imageBuffer;

	public WeatherComponent(Weather component, MirrorModel model) {
		super(component, model);
		imageBuffer = new HashMap<>();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(TEXT);
		Font mainFont = component.getMainFont();
		Font dataFont = component.getDataFont();

		FontMetrics fmMain = g.getFontMetrics(mainFont);
		FontMetrics fmData = g.getFontMetrics(dataFont);

		if (component.hasData()) {
			int textHeight = fmMain.getHeight() + fmData.getHeight();
			int topPadding = getHeight() / 2 - textHeight / 2;

			String temp = String.format("%d°C", component.getTemp());
			String wind = String.format("%d m/s", component.getWindSpeed());
			String town = component.getTown();
			String data = String.format("%s %s", town, wind);
			Image icon = loadImage();

			int imageSize = fmMain.getHeight();
			int leftPadding = getWidth() / 2 - (imageSize + fmMain.stringWidth(temp)) / 2;

			g.setFont(mainFont);
			g.drawImage(icon, leftPadding, topPadding + (textHeight - imageSize) / 2, imageSize, imageSize, null);
			g.drawString(temp, leftPadding + imageSize, topPadding + fmMain.getAscent());

			g.setFont(dataFont);
			g.drawString(data, leftPadding + imageSize, topPadding + fmMain.getHeight() + fmData.getAscent());
		}
	}

	@Override
	protected void updateComponent() {

	}

	private Image loadImage() {
		String icon = component.getIcon();
		BufferedImage img = null;
		img = imageBuffer.get(icon);
		if (img != null) {
			return img;
		}
		try {
			img = ImageIO.read(new File("icons/" + icon + ".png"));
		} catch (IOException e) {
		}
		if (img != null) {
			imageBuffer.put(icon, img);
		}
		return img;
	}

}
