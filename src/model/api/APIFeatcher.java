package model.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class APIFeatcher extends Thread {

	private APIModel model;
	private int delay;

	/**
	 * 
	 * @param model
	 * @param delay
	 *            delay between api fetches in seconds
	 */
	public APIFeatcher(APIModel model, int delay) {
		this.model = model;
		this.delay = delay;
		start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				try {
					URL url = new URL(model.getURL());

					BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					StringBuilder sb = new StringBuilder();
					String str;
					while ((str = in.readLine()) != null) {
						sb.append(str);
					}
					in.close();
					model.presentData(sb.toString());
				} catch (MalformedURLException e) {
				} catch (IOException e) {
				}

				Thread.sleep(delay * 1000);
			}
		} catch (InterruptedException e) {
			System.err.printf("Thread problem, stoped api fetcher for %s%n", model.getURL());
		}
	}
}
