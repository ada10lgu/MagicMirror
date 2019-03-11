package mirror.model.resource;

import org.json.JSONObject;

import mirror.model.Config;
import mirror.model.api.API;
import mirror.model.api.APIRequest;
import mirror.model.api.APIResponse;

public abstract class APIResource extends Resource {

	protected Config config;

	private boolean running;
	private long delay;

	public APIResource(JSONObject data, Config config) {
		super(data);
		this.config = config;
	}

	private String getData() {
		String path = data.getString("path");
		APIRequest request = new APIRequest(config.getServerSettings(), path);
		APIResponse response = API.request(request);
		return response.getBody();
	}

	protected abstract void update(JSONObject response);

	public void update() {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				String data = getData();
				JSONObject obj = new JSONObject(data);
				update(obj);
			}
		};

		new Thread(r).start();
	}

	/**
	 * 
	 * @param time
	 *            seconds between update
	 */
	public synchronized void start(long time) {
		if (running)
			return;
		running = true;
		delay = time;
		Runnable r = new Runnable() {

			@Override
			public void run() {
				while (APIResource.this.isRunning()) {
					String data = getData();
					JSONObject obj = new JSONObject(data);
					update(obj);
					try {
						Thread.sleep(delay * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(r).start();
	}

	public synchronized void stop() {
		running = false;
	}

	private synchronized boolean isRunning() {
		return running;
	}

}
