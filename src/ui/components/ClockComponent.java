package ui.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.component.Clock;

@SuppressWarnings("serial")
public class ClockComponent extends MirrorComponent<Clock> {

	private final String[] MONTH = new String[] { "januari", "februari",
			"mars", "april", "maj", "juni", "juli", "augusti", "september",
			"oktober", "november", "december" };
	private final String[] WEEKDAY = new String[] { "söndag", "måndag",
			"tisdag", "onsdag", "torsdag", "fredag", "lördag" };

	public ClockComponent(Clock component) {
		super(component);
	}

	@Override
	protected void updateComponent() {
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		Font timeFont = component.getTimeFont();
		Font dateFont = component.getDateFont();

		FontMetrics fmTime = g.getFontMetrics(timeFont);
		FontMetrics fmDate = g.getFontMetrics(dateFont);

		int textHeight = fmTime.getHeight() + fmDate.getHeight();

		SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
		Date now = new Date();
		String time = sdfDate.format(now);

		Calendar c = Calendar.getInstance();
		String weekday = WEEKDAY[c.get(Calendar.DAY_OF_WEEK) - 1];
		String day = "" + c.get(Calendar.DAY_OF_MONTH);
		String month = MONTH[c.get(Calendar.MONTH)];
		String date = String.format("%s %s %s", weekday, day, month);

		g.setColor(TEXT);
		{
			g.setFont(timeFont);
			int x = getWidth() / 2 - fmTime.stringWidth(time) / 2;
			int y = (getHeight() - textHeight) / 2 + fmTime.getHeight()
					- fmDate.getHeight();
			g.drawString(time, x, y);
		}
		{
			g.setFont(dateFont);
			int x = getWidth() / 2 - fmDate.stringWidth(date) / 2;
			int y = (getHeight() - textHeight) / 2 + fmTime.getHeight();
			g.drawString(date, x, y);
		}

	}

}
