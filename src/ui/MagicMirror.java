package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.json.JSONException;

import controller.MirrorController;
import model.MirrorModel;

@SuppressWarnings("serial")
public class MagicMirror extends JFrame {

	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	public MagicMirror(MirrorModel model) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setSize(width, height);
		setLocation(0, 0);
		add(new BasePane(model, width, height));
		device.setFullScreenWindow(this);

		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		getContentPane().setCursor(blankCursor);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		File json = new File("settings.json");
		boolean debug = false;
		boolean help = false;
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-s":
			case "--settings":
				json = new File(args[++i]);
				break;
			case "-d":
			case "--debug":
				debug = true;
				break;
			case "-h":
			case "?":
			case "--help":
				help = true;
				break;
			}
		}

		if (help) {
			System.out.println("-d\t--debug\t\ttoggle debug");
			System.out.println("-s\t--settings FILE\tspecify settings file");
			System.out.println("-h\t--help\t\tThis message");
			System.exit(0);
		}

		if (!json.exists()) {
			System.err.printf("File %s does not exist%n", json);
			System.exit(1);
		}

		MirrorModel model = null;
		try {
			model = new MirrorModel(json, debug);
		} catch (FileNotFoundException e) {
			System.err.println("Could not read file");
			System.exit(1);
		} catch (JSONException e) {
			System.err.println(String.format("Could not parse file (%s)", e.getMessage()));
			System.exit(1);
		}

		new MagicMirror(model);
		new MirrorController(model);
		System.out.println("Magic mirror started");

	}
}
