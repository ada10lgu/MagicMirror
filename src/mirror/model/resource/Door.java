package mirror.model.resource;

import org.json.JSONObject;

import mirror.model.Config;

public class Door extends APIResource {

	private boolean open;
	private String name;

	public Door(JSONObject data, Config config) {
		super(data, config);
		start(1);
	}

	@Override
	protected void update(JSONObject response) {
		open = response.getBoolean("open");
		name = response.getString("name");
	}

	public boolean isOpend() {
		return open;
	}

	@Override
	public String getInfo() {
		return name;
	}

	@ResourceAction(name = "status")
	public void status() {
		System.out.printf("%s\nIt is %s\n", name, open ? "open" : "closed");
	}

	@ResourceAction(name = "open")
	public void open() {
		System.out.println("Opening door");
	}

}