package model.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Nameday extends Text {

	private JSONObject nameday;

	public Nameday(JSONObject json) {
		super(json);
		JSONObject data = json.getJSONObject("data");
		verify(data, "json");

		StringBuilder sb = new StringBuilder();
		Scanner s = null;

		try {
			s = new Scanner(new File(data.getString("json")));
			while (s.hasNext()) {
				sb.append(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not read nameday.json file");
		}

		nameday = new JSONObject(sb.toString());
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
