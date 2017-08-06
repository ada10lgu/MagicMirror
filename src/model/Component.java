package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

public class Component {
	private String type;
	private int x;
	private int y;
	private int w;
	private int h;

	private HashMap<String, Object> extras;

	public Component(JSONObject json) {
		check(json, "type");
		type = json.getString("type");

		check(json, "position");
		JSONObject pos = json.getJSONObject("position");
		check(pos, "x");
		x = pos.getInt("x");
		check(pos, "y");
		y = pos.getInt("y");

		check(json, "size");
		JSONObject size = json.getJSONObject("size");
		check(size, "w");
		w = size.getInt("w");
		check(size, "h");
		h = size.getInt("h");

		ArrayList<String> keys = new ArrayList<>(json.keySet());
		keys.remove("type");
		keys.remove("position");
		keys.remove("size");

		extras = new HashMap<>();
		for (String key : keys) {
			extras.put(key, json.get(key));
		}

	}

	private void check(JSONObject json, String key) {
		if (!json.has(key))
			throw new IllegalArgumentException("Missing " + key);
	}
}
