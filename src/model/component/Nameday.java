package model.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Nameday extends Text {

	private JSONObject nameday;

	public Nameday(JSONObject json) {
		super(json);
		JSONObject data = json.getJSONObject("data");
		verify(data, "json");

		StringBuilder sb = new StringBuilder();
		File f = new File(data.getString("json"));

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			int c;
			while ((c = br.read()) != -1) {
				sb.append((char) c);
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Could not read " + f + " file due to encoding errors\n");
		} catch (IOException e) {
			throw new RuntimeException("Could not read " + f + " file\n");

		}
		try {
			nameday = new JSONObject(sb.toString());
		} catch (JSONException e) {
			nameday = new JSONObject();
			System.err.printf("Could not parse file, %s\nError: %s\n", f, e.getMessage());
		}
	}

	@Override
	public String getText() {

		Calendar c = Calendar.getInstance();
		String day = "" + c.get(Calendar.DAY_OF_MONTH);
		String month = "" + c.get(Calendar.MONTH);

		if (!nameday.has(month))
			return "";

		JSONObject jsonMonth = nameday.getJSONObject(month);

		if (!jsonMonth.has(day))
			return "";

		JSONArray names = jsonMonth.getJSONArray(day);

		if (names.length() == 0)
			return "";

		StringBuilder sb = new StringBuilder();

		sb.append(names.get(0));
		for (int i = 1; i < names.length(); i++) {
			sb.append(", ");
			sb.append(names.get(i));
		}

		return sb.toString();
	}

}
