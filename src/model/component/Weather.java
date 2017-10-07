package model.component;

import java.awt.Font;

import org.json.JSONObject;

import model.api.APIFeatcher;
import model.api.APIModel;

public class Weather extends Component implements APIModel {

	private JSONObject weather;

	public Weather(JSONObject json) {
		super(json);

		verify(json, "data");
		JSONObject data = json.getJSONObject("data");
		verifyFont(data, "font");

		verify(data, "api");
		verify(data, "key");
		verify(data, "town");

		new APIFeatcher(this, 10);
	}

	public String getURL() {
		JSONObject data = json.getJSONObject("data");
		String api = data.getString("api");
		String key = data.getString("key");
		String town = data.getString("town");

		return String.format("%s?%s&%s", api, key, town);
	}

	@Override
	public synchronized void presentData(String s) {
		weather = new JSONObject(s);
	}

	public synchronized int getTemp() {
		return weather.getJSONObject("main").getInt("temp") - 273;
	}

	public synchronized boolean hasData() {
		return weather != null;
	}

	public Font getFont() {
		return getFont(json.getJSONObject("data").getJSONObject("font"));
	}

}
