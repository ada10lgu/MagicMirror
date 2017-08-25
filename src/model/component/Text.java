package model.component;

import java.awt.Font;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Text extends Component {

	private int index;
	private Random rand = new Random();

	public Text(JSONObject json) {
		super(json);
		verify(json, "data");
		JSONObject data = json.getJSONObject("data");
		verifyFont(data, "font");
		verify(data, "text");
		verify(data, "random");
		verify(data, "delay");

		index = -1;
	}

	public int getDelay() {
		return json.getJSONObject("data").getInt("delay") * 1000;
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

	public String getNext() {
		JSONArray array = json.getJSONObject("data").getJSONArray("text");
		if (array.length() == 0) {
			return "";
		}
		boolean random = json.getJSONObject("data").getBoolean("random");
		if (random) {
			index = rand.nextInt(array.length());
		} else {
			index++;
			index %= array.length();
		}

		return array.getString(index);
	}
}
