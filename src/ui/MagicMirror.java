package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

import org.json.JSONArray;
import org.json.JSONObject;

import ui.components.ClockComponent;
import ui.components.ExitButton;
import ui.components.TextComponent;
import ui.components.UnknownComponent;

@SuppressWarnings("serial")
public class MagicMirror extends JFrame {
	
	static GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];

	public MagicMirror(List<MirrorComponent> components) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setSize(width, height);
		setLocation(0, 0);
		add(new BasePane(components));
		device.setFullScreenWindow(this);
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		getContentPane().setCursor(blankCursor);
		
		setVisible(true);
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(System.getProperty("os.name"));
		
		
		File json = new File("settings.json");

		if (args.length > 0) {
			json = new File(args[0]);
		}

		JSONArray settings = loadSettings(json);

		ArrayList<MirrorComponent> components = new ArrayList<>();

		System.out.println("Magic mirror started");
		
		for (Object o : settings) {
			JSONObject object = (JSONObject) o;
			MirrorComponent component = null;
			switch (object.getString("type")) {
			case "exit":
				component = new ExitButton(null);
				break;
			case "clock":
				component = new ClockComponent(object.getJSONObject("fonts"));
				break;
			case "text":
				component = new TextComponent(object.getJSONObject("data"));
				break;
			default:
				component = new UnknownComponent(null);
			}
			int xAnchor = 0;
			int yAnchor = 0;
			if (object.has("anchor")) {
				JSONObject anchor = object.getJSONObject("anchor");
				xAnchor = getAnchor(anchor.get("x"));
				yAnchor = getAnchor(anchor.get("y"));
			}

			Rectangle r = new Rectangle();
			r.x = object.getJSONObject("position").getInt("x") + xAnchor;
			r.y = object.getJSONObject("position").getInt("y") + yAnchor;
			r.width = object.getJSONObject("size").getInt("w");
			r.height = object.getJSONObject("size").getInt("h");
			component.setBounds(r);
			components.add(component);
		}

		new MagicMirror(components);
	}

	private static JSONArray loadSettings(File f) throws FileNotFoundException {
		Scanner s = new Scanner(f);
		StringBuilder sb = new StringBuilder();
		while (s.hasNext()) {
			sb.append(s.nextLine());
		}
		s.close();
		return new JSONArray(sb.toString());
	}

	private static int getAnchor(Object json) {
		try {
			return Integer.parseInt(json.toString());
		} catch (NumberFormatException e) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			switch (json.toString().toLowerCase()) {
			case "left":
				return 0;
			case "right":
				return screenSize.width;
			case "top":
				return 0;
			case "bottom":
				return screenSize.height;
			case "bottom/2":
				return screenSize.height / 2;
			case "right/2":
				return screenSize.width / 2;
			}
		}
		return 0;
	}
}
