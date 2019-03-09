package mirror.model.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class APIRequest {
	String path;
	Map<String, String> headers;

	public APIRequest(JSONObject serverInfo, String path) {
		if (serverInfo == null) {
			throw new NullPointerException("Cant send null server info");
		}

		headers = new HashMap<>();
		String server = serverInfo.getString("host");
		int port = serverInfo.getInt("port");
		this.path = String.format("%s:%d%s", server, port, path);
		if (serverInfo.has("session")) {
			addHeader("session-id", serverInfo.getString("session"));
		}
	}
	
	public String getPath() {
		return path;
	}

	public void addHeader(String header, String text) {
		headers.put(header, text);
	}
}
