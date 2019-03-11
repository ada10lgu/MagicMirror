package mirror.gui.components;

import java.awt.Graphics2D;
import java.awt.Image;

import mirror.gui.ImageLoader;
import mirror.model.Config;
import mirror.model.resource.Door;

@SuppressWarnings("serial")
public class DoorComponent extends Component<Door> {

	private ImageLoader imageLoader;

	public DoorComponent(Door resource, Config config, ImageLoader imageLoader) {
		super(resource, config);
		this.imageLoader = imageLoader;
	}

	@Override
	protected void paintBody(Graphics2D g) {
		if (!resource.isOpend())
			return;

		Image img = imageLoader.getImage("door");

		int w = img.getWidth(null);
		int h = img.getHeight(null);

		double ratio = ((double) w) / h;

		if (w > getWidth()) {
			w = getWidth();
			h = (int) (w / ratio);
		}
		if (h > getHeight()) {
			h = getHeight();
			w = (int) (ratio * h);
		}

		int x = getWidth() / 2 - w / 2;
		int y = getHeight() / 2 - h / 2;

		g.drawImage(img, x, y, w, h, null);
	}

}
