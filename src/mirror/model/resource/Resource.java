package mirror.model.resource;

import java.util.UUID;

import org.json.JSONObject;

public abstract class Resource {
	
	protected JSONObject data;
	
	public Resource(JSONObject data) {
		this.data = data;
	}
	
	public UUID getID() {
		return UUID.fromString(data.getString("id"));
	}
}
