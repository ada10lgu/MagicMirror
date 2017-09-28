package model.component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

import org.json.JSONObject;

public class Nameday extends Text {

	private static final File NAMEDAY_FILE = new File("nameday.json");

	private JSONObject nameday;

	public Nameday(JSONObject json) {
		super(json);
		JSONObject data = json.getJSONObject("data");
		verify(data, "api");

		StringBuilder sb = new StringBuilder();
		Scanner s = null;

		try {
			s = new Scanner(NAMEDAY_FILE);
			while (s.hasNext()) {
				sb.append(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not read nameday.json file");
		}

		nameday = new JSONObject(sb.toString());
		System.out.println(nameday);
	}

	@Override
	public String getText() {

		Calendar c = Calendar.getInstance();
		String day = "" + c.get(Calendar.DAY_OF_MONTH);
		String month = "" + c.get(Calendar.MONTH);

		if (!nameday.has(month)) 
			return "";
		
		JSONObject jsonMonth = nameday.getJSONObject(month);
		System.out.println(jsonMonth);
		
		return "lars,lisa";
	}

}
