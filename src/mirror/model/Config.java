package mirror.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class Config {

	private File file;
	private JSONObject data;

	public Config(String filename) {
		file = new File(filename);
	}

	public void load() throws IOException {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = in.read()) != -1) {
			sb.append((char) c);
		}
		in.close();
		data = new JSONObject(sb.toString());
	}

	public void save() throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
		writer.write(data.toString(2));
		writer.flush();
		writer.close();
	}

	public JSONObject getServerSettings() {
		return data.getJSONObject("server");
	}

	public JSONObject getGUISettings() {
		return data.getJSONObject("screen");
	}

	public JSONArray getComponents() {
		return data.getJSONArray("components");
	}

}
