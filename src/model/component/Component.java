package model.component;

import java.awt.Font;
import java.awt.Rectangle;

import org.json.JSONException;
import org.json.JSONObject;

public class Component {

	protected JSONObject json;

	public Component(JSONObject json) {
		this.json = json;
		verify(json, "type");
		verify(json, "size");
		JSONObject size = json.getJSONObject("size");
		verify(size, "w");
		verify(size, "h");
		verify(json, "position");
		JSONObject position = json.getJSONObject("position");
		verify(position, "x");
		verify(position, "y");
	}

	public JSONObject getJSON() {
		return json;
	}

	public String getType() {
		return json.getString("type").toLowerCase();
	}

	public Rectangle getBounds(int width, int height) {
		JSONObject size = json.getJSONObject("size");
		int w = size.getInt("w");
		int h = size.getInt("h");
		JSONObject position = json.getJSONObject("position");
		int x = position.getInt("x");
		int y = position.getInt("y");
		if (json.has("anchor")) {
			JSONObject anchor = json.getJSONObject("anchor");
			verify(anchor, "x");
			verify(anchor, "y");
			int xAnchor = parseAnchor(anchor.getString("x"), width, height);
			int yAnchor = parseAnchor(anchor.getString("y"), width, height);
			x += xAnchor;
			y += yAnchor;
		}
		return new Rectangle(x, y, w, h);
	}

	protected void verify(JSONObject json, String key) {
		if (!json.has(key))
			throw new JSONException(String.format("Missing key, %s", key));
	}

	protected void verifyFont(JSONObject json, String key) {
		verify(json, key);
		JSONObject font = json.getJSONObject(key);
		if (!font.has("font") || !font.has("size"))
			throw new JSONException("Not a valid font format");
	}

	protected Font getFont(JSONObject json) {
		String name = json.getString("font");
		int size = json.getInt("size");
		return new Font(name, Font.PLAIN, size);
	}

	private int parseAnchor(String anchor, int width, int height) {
		try {
			return Integer.parseInt(anchor);
		} catch (NumberFormatException e) {
			switch (anchor.toLowerCase()) {
			case "left":
				return 0;
			case "right":
				return width;
			case "top":
				return 0;
			case "bottom":
				return height;
			case "bottom/2":
				return height / 2;
			case "right/2":
				return width / 2;
			}
		}
		return 0;
	}

	public static Component create(JSONObject json) {
		Component component = new Component(json);

		switch (component.getType()) {
		case "clock":
			component = new Clock(json);
			break;
		case "text":
			component = new StaticText(json);
			break;
		case "nameday":
			component = new Nameday(json);
			break;
		case "house":
			component = new House(json);
			break;
		}

		return component;
	}

}
