package mirror.gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import mirror.model.Config;

public class ImageLoader {

	private Config config;

	private Map<String, Image> images;

	public ImageLoader(Config config) {
		this.config = config;
	}

	public void load() throws IOException {
		File folder = config.getImagePath();
		if (!folder.exists())
			folder.mkdir();

		images = new HashMap<>();
		System.out.println("Loading images...");
		for (File file : folder.listFiles()) {
			Image image = ImageIO.read(file);
			String name = file.getName().substring(0, file.getName().indexOf("."));
			images.put(name, image);
		}
		System.out.printf("Loaded %d image(s).\n", images.size());
	}

	public Image getImage(String image) {
		return images.get(image);
	}

}
