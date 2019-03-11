package mirror.model.resource;

import org.json.JSONObject;

import mirror.model.Config;

public class Door extends APIResource {

	private boolean open;

	
	public Door(JSONObject data, Config config) {
		super(data, config);
		start(5);
	}

	@Override
	protected void update(JSONObject response) {
		open = response.getBoolean("open");
	}

	public boolean isOpend() {
		return open;
	}
}
