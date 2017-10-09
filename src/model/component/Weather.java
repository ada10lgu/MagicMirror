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
		verify(data, "fonts");
		JSONObject fonts = data.getJSONObject("fonts");
		verifyFont(fonts, "main");
		verifyFont(fonts, "data");

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
		// System.out.println(weather.toString(4));
		// System.out.printf("Temp:\t%d%nSpeed:\t%d%nIcon:\t%s%n", getTemp(),
		// getWindSpeed(), getIcon());
		// System.out.println(getTown());
	}

	public synchronized int getTemp() {
		return (int) Math.round(weather.getJSONObject("main").getDouble("temp") - 273.15);
	}

	public synchronized int getWindSpeed() {
		return (int) Math.round(weather.getJSONObject("wind").getDouble("speed"));
	}

	public String getIcon() {
		return weather.getJSONArray("weather").getJSONObject(0).getString("icon");
	}

	public String getTown() {
		return weather.getString("name");
	}

	public synchronized boolean hasData() {
		return weather != null;
	}

	public Font getMainFont() {
		return getFont(json.getJSONObject("data").getJSONObject("fonts").getJSONObject("main"));
	}

	public Font getDataFont() {
		return getFont(json.getJSONObject("data").getJSONObject("fonts").getJSONObject("data"));
	}

}
