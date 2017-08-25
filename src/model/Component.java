package model;

import org.json.JSONObject;

public class Component {

	private JSONObject json;

	public Component(JSONObject json) {
		this.json = json;
		check(json, "type");
		check(json, "position");
		JSONObject pos = json.getJSONObject("position");
		check(pos, "x");
		check(pos, "y");
		check(json, "size");
		JSONObject size = json.getJSONObject("size");
		check(size, "w");
		check(size, "h");
	}

	private void check(JSONObject json, String key) {
		if (!json.has(key))
			throw new IllegalArgumentException("Missing " + key);
	}

	public int getX() {
		return json.getJSONObject("position").getInt("x");
	}

	public int getY() {
		return json.getJSONObject("position").getInt("y");
	}

	JSONObject getJSON() {
		return json;
	}
}
