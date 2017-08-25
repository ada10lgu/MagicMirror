package model.component;

import org.json.JSONObject;

public class Nameday extends Text {

	public Nameday(JSONObject json) {
		super(json);
		JSONObject data = json.getJSONObject("data");
		verify(data, "api");
	}

	@Override
	public String getText() {
		return "lars,lisa";
	}

}
