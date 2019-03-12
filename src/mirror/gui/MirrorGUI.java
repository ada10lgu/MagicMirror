package mirror.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.json.JSONObject;

import mirror.model.Config;
import mirror.model.MirrorModel;

@SuppressWarnings("serial")
public class MirrorGUI extends JFrame {

	public MirrorGUI(Config config, MirrorModel model, ImageLoader imageLoader) {
		System.out.println("Loading UI");
		JSONObject settings = config.getGUISettings();
		
		boolean fullscreen = settings.getBoolean("fullscreen");
		if (fullscreen) {
		
		int preferedScreen = settings.has("preferedScreen") ? settings.getInt("preferedScreen") : 0;
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		if (devices.length <= preferedScreen) {
			preferedScreen = 0;
			System.out.println("Prefered screen could not be matched, dafaulting back to screen 0");
		}
		GraphicsDevice device = devices[preferedScreen];

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		setSize(width, height);
		setLocation(0, 0);
		device.setFullScreenWindow(this);
		} else {
			setTitle("Magic Mirror");
			setSize(800,600);
			setLocation(0,0);
		}
		boolean showCursor = settings.has("showCursor") ? settings.getBoolean("showCursor") : true;

		if (!showCursor) {
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
					"blank cursor");
			getContentPane().setCursor(blankCursor);
		}

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(new BasePane(model, config, imageLoader));

		setVisible(true);
	}
}