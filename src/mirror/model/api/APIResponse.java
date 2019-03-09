package mirror.model.api;

import java.util.HashMap;
import java.util.Map;

public class APIResponse {
	int responseCode;
	Map<String, String> headers;
	String body;

	public APIResponse() {
		headers = new HashMap<>();
	}

	public String getBody() {
		return body;
	}

	public int getCode() {
		return responseCode;
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

}
