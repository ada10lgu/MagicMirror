package mirror.model.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class API {

	public static APIResponse request(APIRequest request) {
		HttpURLConnection connection = null;
		APIResponse response = new APIResponse();
		try {
			URL url = new URL(request.path);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(request.getMethod());
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			for (String key : request.headers.keySet()) {
				connection.setRequestProperty(key, request.headers.get(key));
			}
			connection.setUseCaches(false);

			if (!request.getMethod().equalsIgnoreCase("GET")) {
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(request.body);
				out.close();
			}

			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			int c;
			while ((c = rd.read()) != -1) {
				sb.append((char) c);
			}
			rd.close();
			response.body = sb.toString();
			response.responseCode = connection.getResponseCode();

			Map<String, List<String>> headers = connection.getHeaderFields();
			for (String key : headers.keySet()) {
				response.headers.put(key, connection.getHeaderField(key));
			}
		} catch (Exception e) {
			try {
				response.responseCode = connection.getResponseCode();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

}
