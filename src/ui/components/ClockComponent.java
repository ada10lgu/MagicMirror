package ui.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import ui.MirrorComponent;

@SuppressWarnings("serial")
public class ClockComponent extends MirrorComponent {

	private final String[] MONTH = new String[] { "januari", "februari", "mars", "april", "maj", "juni", "juli",
			"augusti", "september", "oktober", "november", "december" };
	private final String[] WEEKDAY = new String[] { "söndag", "måndag", "tisdag", "onsdag", "torsdag", "fredag",
			"lördag" };

	private Font timeFont;
	private Font dateFont;

	private String time = "00:00";
	private String date = "";

	public ClockComponent(JSONObject extra) {
		super(extra);

		JSONObject time = extra.getJSONObject("time");
		timeFont = extract(time);
		JSONObject date = extra.getJSONObject("date");
		dateFont = extract(date);

		start(100);
	}


	@Override
	protected void work() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
		Date now = new Date();
		time = sdfDate.format(now);

		Calendar c = Calendar.getInstance();
		String weekday = WEEKDAY[c.get(Calendar.DAY_OF_WEEK) - 1];
		String day = "" + c.get(Calendar.DAY_OF_MONTH);
		String month = MONTH[c.get(Calendar.MONTH)];
		date = String.format("%s %s %s", weekday, day, month);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(TEXT);

		{
			g.setFont(timeFont);
			FontMetrics fm = g.getFontMetrics();
			int x = getWidth() / 2 - fm.stringWidth(time) / 2;
			int y = getHeight() / 2;
			g.drawString(time, x, y);
		}
		{
			g.setFont(dateFont);
			FontMetrics fm = g.getFontMetrics();
			int x = getWidth() / 2 - fm.stringWidth(date) / 2;
			int y = getHeight() / 2 + fm.getHeight();
			g.drawString(date, x, y);
		}
	}

}
