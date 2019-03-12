package mirror.model.resource;

import org.json.JSONObject;

import mirror.model.Config;
import mirror.model.api.API;
import mirror.model.api.APIRequest;
import mirror.model.api.APIResponse;

public class Door extends APIResource {

	private JSONObject info;

	public Door(JSONObject data, Config config) {
		super(data, config);
		start(1);
	}

	@Override
	protected void update(JSONObject response) {
		info = response;
	}

	public boolean isOpend() {
		return info.has("open") ? info.getBoolean("open") : false;
	}

	@Override
	public String getInfo() {
		return info.has("name") ? info.getString("name") : null;
	}

	@ResourceAction(name = "status")
	public void status() {
		System.out.printf("%s (%s)\n", getInfo(), isOpend() ? "opened" : "closed");
	}

	@ResourceAction(name = "open")
	public void open() {
		System.out.println("Opening door");

		JSONObject payload = new JSONObject(info, JSONObject.getNames(info));
		payload.put("open", true);

		String path = data.getString("path");
		String body = payload.toString();
		APIRequest request = new APIRequest(config.getServerSettings(), path, "PUT", body);
		APIResponse response = API.request(request);

		if (response.getCode() == 200)
			System.out.println("Door opend!");
		else
			System.out.println("error...");
	}

}