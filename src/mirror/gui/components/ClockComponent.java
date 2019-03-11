package mirror.gui.components;

import java.awt.Font;
import java.awt.FontMetrics;
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
		Font timeFont = getFont(resource.getTimeSize());
		Font dateFont = getFont(resource.getDateSize());
		FontMetrics timeFM = g.getFontMetrics(timeFont);
		FontMetrics dateFM = g.getFontMetrics(dateFont);

		g.setColor(TEXT);

		String time = getTime();
		String date = getDate();

		int height = timeFM.getHeight() + dateFM.getHeight();
		int width = Math.max(timeFM.stringWidth(time), dateFM.stringWidth(date));
		int x = getWidth() / 2 - width / 2;
		int y = getHeight() / 2 - height / 2;

		if (config.isDebug()) {
			g.drawRect(x, y, width - 1, height - 1);
		}

		int timeX = x + width / 2 - timeFM.stringWidth(time) / 2;
		int timeY = y + timeFM.getHeight() - timeFM.getDescent();
		g.setFont(timeFont);
		g.drawString(time, timeX, timeY);

		int dateX = x + width / 2 - dateFM.stringWidth(date) / 2;
		int dateY = y + height - dateFM.getDescent();

		g.setFont(dateFont);
		g.drawString(date, dateX, dateY);

	}

	private String getTime() {
		String time = String.format("%02d:%02d:%02d", resource.getHour(), resource.getMinute(), resource.getSecond());
		return time;
	}

	private String getDate() {
		String date = String.format("%d/%d/%d", resource.getDay(), resource.getMonth(), resource.getYear());
		return date;
	}

}
