package model.component;

import java.awt.Font;

import org.json.JSONObject;

public class Clock extends Component {

	public Clock(JSONObject json) {
		super(json);
		verify(json, "fonts");
		JSONObject fonts = json.getJSONObject("fonts");
		verifyFont(fonts, "time");
		verifyFont(fonts, "date");
	}

	public Font getTimeFont() {
		return getFont(json.getJSONObject("fonts").getJSONObject("time"));
	}

	public Font getDateFont() {
		return getFont(json.getJSONObject("fonts").getJSONObject("date"));
	}

}
