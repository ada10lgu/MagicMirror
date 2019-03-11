package mirror.gui.components;

import java.awt.Graphics2D;

import mirror.model.Config;
import mirror.model.resource.Resource;

@SuppressWarnings("serial")
public class UnknownComponent extends Component<Resource> {

	public UnknownComponent(Resource resource, Config config) {
		super(resource, config);
	}

	@Override
	protected void paintBody(Graphics2D g) {
		g.setColor(TEXT);
		g.fillRect(10, 10, 20, 20);
	}

}
