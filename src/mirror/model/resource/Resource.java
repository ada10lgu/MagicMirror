package mirror.model.resource;

import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import mirror.model.Config;

public abstract class Resource {

	protected JSONObject data;

	public Resource(JSONObject data) {
		this.data = data;

		if (!data.has("dimension")) {
			System.out.println("No dimension data found, adding default");
			JSONObject dimension = new JSONObject();
			dimension.put("x", 0);
			dimension.put("y", 0);
			dimension.put("w", 100);
			dimension.put("h", 100);
			data.put("dimension", dimension);
		}

	}

	public Rectangle getBounds() {
		Rectangle rect = new Rectangle();
		JSONObject dimension = data.getJSONObject("dimension");
		rect.x = dimension.getInt("x");
		rect.y = dimension.getInt("y");
		rect.width = dimension.getInt("w");
		rect.height = dimension.getInt("h");
		return rect;
	}

	public String getType() {
		return data.getString("type");
	}

	public UUID getID() {
		return UUID.fromString(data.getString("id"));
	}

	public boolean isRemote() {
		return data.has("remote") ? data.getBoolean("remote") : false;
	}

	public void updateRemoteData(JSONObject data) {
		for (String key : data.keySet()) {
			data.put(key, data.get(key));
		}
	}

	public abstract String getInfo();

	public static Resource create(JSONObject comp, Config config) {
		switch (comp.getString("type")) {
		case "door":
			return new Door(comp, config);
		case "clock":
			return new Clock(comp);
		default:
			throw new IllegalArgumentException(comp.toString());
		}
	}

	public List<String> getActions() {
		List<String> actions = new ArrayList<>();

		for (Method method : getClass().getMethods()) {
			ResourceAction[] list = method.getAnnotationsByType(ResourceAction.class);
			for (ResourceAction item : list) {
				actions.add(item.name());
			}
		}

		return actions;
	}

	public void performAction(String string) {
		for (Method method : getClass().getMethods()) {
			ResourceAction[] list = method.getAnnotationsByType(ResourceAction.class);
			for (ResourceAction item : list) {
				if (item.name().equalsIgnoreCase(string)) {
					try {
						method.invoke(this);
						return;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.printf("Illegal instruction (%s).",string);
	}
}
