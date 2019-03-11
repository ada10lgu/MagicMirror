package mirror.model.resource;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONObject;

public class Clock extends Resource {

	public Clock(JSONObject data) {
		super(data);
	}

	public int getHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		return Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
	}

	public int getMinute() {
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
		return Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
	}

	public int getSecond() {
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		return Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
	}

	public int getDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
	}

	public int getMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("M");
		return Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
	}

	public int getYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return Integer.parseInt(sdf.format(Calendar.getInstance().getTime()));
	}

	public int getTimeSize() {
		return data.getInt("timeSize");
	}

	public int getDateSize() {
		return data.getInt("dateSize");
	}
}
