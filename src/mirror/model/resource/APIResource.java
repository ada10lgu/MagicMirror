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

	private APIResponse getData() {
		String path = data.getString("path");
		APIRequest request = new APIRequest(config.getServerSettings(), path);
		APIResponse response = API.request(request);
		return response;
	}

	protected abstract void update(JSONObject response);

	public void update() {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				APIResponse response = getData();
				if (response.getCode() == 200) {
					String data = response.getBody();
					JSONObject obj = null;
					if (data != null) {
						obj = new JSONObject(data);
					}
					update(obj);
				} else {
					System.out.printf("Error when fetching data (code:%d) (type:%s)\n", response.getCode(), getType());
				}
			}
		};

		new Thread(r).start();
	}

	/**
	 * 
	 * @param time
	 *            seconds between update
	 */
	public synchronized void start() {
		long time = getPeriod();
		if (running)
			return;
		running = true;
		delay = time;
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while (APIResource.this.isRunning()) {
					APIResponse response = getData();
					if (response.getCode() == 200) {
						String data = response.getBody();
						JSONObject obj = null;
						if (data != null) {
							obj = new JSONObject(data);
						}
						update(obj);
					} else {
						System.out.printf("Error when fetching data (code:%d) (type:%s)\n", response.getCode(),
								getType());
					}
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
	
	protected abstract int getPeriod();

}
