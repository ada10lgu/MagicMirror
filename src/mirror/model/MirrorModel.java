package mirror.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import mirror.controller.LoginController;
import mirror.model.api.API;
import mirror.model.api.APIRequest;
import mirror.model.api.APIResponse;
import mirror.model.resource.Resource;

public class MirrorModel {

	private Config config;
	private Map<UUID, Resource> resources;

	public MirrorModel(Config config) {
		this.config = config;
		resources = new HashMap<>();
	}

	public boolean login(LoginController controller) throws NoSuchAlgorithmException, IOException {
		JSONObject serverInfo = config.getServerSettings();

		String oldSession = serverInfo.has("session") ? serverInfo.getString("session") : null;
		if (oldSession != null) {
			System.out.print("Found stored session... verifying... ");
			if (verifySession()) {
				System.out.println("verified!");
				return true;
			} else {
				System.out.println("invalid.");
				serverInfo.remove("session");
				config.save();
			}
		}

		APIRequest challengeRequest = new APIRequest(serverInfo, Constants.CHALLENGE_PATH);
		challengeRequest.addHeader("user", serverInfo.getString("username"));
		APIResponse challengeResponse = API.request(challengeRequest);
		if (challengeResponse.getCode() != 200) {
			return false;
		}
		String challenge = challengeResponse.getBody();

		String hash = controller.login(challenge);

		APIRequest loginRequest = new APIRequest(serverInfo, Constants.LOGIN_PATH);
		loginRequest.addHeader("user", serverInfo.getString("username"));
		loginRequest.addHeader("response", hash);
		loginRequest.addHeader("challenge", challenge);

		APIResponse loginResponse = API.request(loginRequest);

		if (loginResponse.getCode() != 200) {
			return false;
		}

		String session = loginResponse.getHeader("session-id");

		serverInfo.put("session", session);
		config.save();
		return true;
	}

	public boolean verifySession() {
		JSONObject serverInfo = config.getServerSettings();
		APIRequest verifyRequest = new APIRequest(serverInfo, Constants.WHO_PATH);
		APIResponse verifyResponse = API.request(verifyRequest);

		if (verifyResponse.getCode() != 200) {
			return false;
		}
		String user = verifyResponse.getBody();
		return user.equals(serverInfo.get("username"));
	}

	public void loadResources() {
		System.out.println("Loading localy stored resources:");
		Map<UUID, Resource> local = new HashMap<>();
		Map<UUID, Resource> remote = new HashMap<>();
		{

			JSONArray data = config.getComponents();
			for (int i = 0; i < data.length(); i++) {
				JSONObject comp = data.getJSONObject(i);
				if (!comp.has("id")) {
					comp.put("id", UUID.randomUUID().toString());
				}
				Resource r = Resource.create(comp);

				if (r.isRemote()) {
					remote.put(r.getID(), r);
				} else {
					local.put(r.getID(), r);
				}
			}
		}

		System.out.printf(" %d local resources found\n", local.size());
		System.out.printf(" %d remote resource references found\n", remote.size());

		{
			System.out.println("Loading remote resources");
			JSONObject serverInfo = config.getServerSettings();
			APIRequest resourceRequest = new APIRequest(serverInfo, Constants.RESOURCES_PATH);
			APIResponse resourceResponse = API.request(resourceRequest);
			JSONObject data = new JSONObject(resourceResponse.getBody());

			if (data.has("doors")) {
				JSONArray list = data.getJSONArray("doors");
				for (int i = 0; i < list.length(); i++) {
					JSONObject comp = list.getJSONObject(i);
					comp.put("type", "door");
					comp.put("remote", true);

					UUID id = UUID.fromString(comp.getString("id"));

					if (remote.containsKey(id)) {
						Resource old = remote.remove(id);
						old.updateRemoteData(comp);
						local.put(old.getID(), old);
					} else {
						Resource r = Resource.create(comp);
						config.getComponents().put(comp);
						local.put(r.getID(), r);
					}
				}
				System.out.printf(" %d door(s) loaded.\n", list.length());
			}
			System.out.println("Load done");
			System.out.printf(" %d resources loaded\n", local.size());
			System.out.printf(" %d remote resource expired\n", remote.size());
		}
		{
			for (UUID id : remote.keySet()) {

				JSONArray data = config.getComponents();
				for (int i = 0; i < data.length(); i++) {
					JSONObject comp = data.getJSONObject(i);
					if (id.equals(UUID.fromString(comp.getString("id")))) {
						data.remove(i);
						break;
					}

				}
			}
		}
		resources.putAll(local);
	}

	public List<Resource> getResources() {
		List<Resource> resources = new ArrayList<>();
		for (UUID id : this.resources.keySet()) {
			resources.add(this.resources.get(id));
		}
		return resources;
	}

}
