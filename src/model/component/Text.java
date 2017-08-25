package model.component;

import java.awt.Font;

import org.json.JSONObject;

public abstract class Text extends Component {

	public Text(JSONObject json) {
		super(json);
		verify(json, "data");
		JSONObject data = json.getJSONObject("data");
		verifyFont(data, "font");

	}

	public Font getFont() {
		return getFont(json.getJSONObject("data").getJSONObject("font"));
	}

	public String getAllign() {
		JSONObject data = json.getJSONObject("data");
		if (data.has("allign")) {
			return json.getJSONObject("data").getString("allign").toLowerCase();
		}
		return "center";
	}

	public abstract String getText();
}
