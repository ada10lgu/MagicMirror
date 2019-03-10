package mirror.model.resource;

import java.util.UUID;

import org.json.JSONObject;

public abstract class Resource {

	protected JSONObject data;

	public Resource(JSONObject data) {
		this.data = data;
	}
	
	public String getType() {
		return data.getString("type");
	}

	public UUID getID() {
		Object o = data.get("id");
		System.out.println(o.getClass());
		
		
		
		return UUID.fromString(data.getString("id"));
	}

	public static Resource create(JSONObject comp) {
		switch (comp.getString("type")) {
		case "door":
			return new Door(comp);
		case "clock":
			return new Clock(comp);
		default:
			throw new IllegalArgumentException(comp.toString());
		}
	}
}
