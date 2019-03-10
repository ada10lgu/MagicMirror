package mirror.gui.components;

import java.awt.Graphics2D;

import mirror.model.Config;
import mirror.model.resource.Clock;

@SuppressWarnings("serial")
public class ClockComponent extends Component<Clock> {

	public ClockComponent(Clock clock, Config config) {
		super(clock, config);
	}

	@Override
	protected void paintBody(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}
}
