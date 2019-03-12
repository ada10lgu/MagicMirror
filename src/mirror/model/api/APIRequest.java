package mirror.model.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class APIRequest {

	String method;
	String path;
	String body;
	Map<String, String> headers;

	public APIRequest(JSONObject serverInfo, String path, String method, String body) {
		if (serverInfo == null) {
			throw new NullPointerException("Cant send null server info");
		}
		this.method = method;
		this.body = body;
		headers = new HashMap<>();
		String server = serverInfo.getString("host");
		int port = serverInfo.getInt("port");
		this.path = String.format("%s:%d%s", server, port, path);
		if (serverInfo.has("session")) {
			addHeader("session-id", serverInfo.getString("session"));
		}
	}

	public APIRequest(JSONObject serverInfo, String path) {
		this(serverInfo, path, "GET", "");
	}

	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public void addHeader(String header, String text) {
		headers.put(header, text);
	}
}
