package mirror.model.resource;

import org.json.JSONObject;

public class Clock extends Resource {

	public Clock(JSONObject data) {
		super(data);
	}

	public int getHour() {
		return 0;

	}

	public int getMinute() {
		return 0;
	}

	public int getSecond() {
		return 0;
	}

	public int getDay() {
		return 0;
	}

	public int getMonth() {
		return 0;
	}

	public int getYear() {
		return 0;
	}
}
