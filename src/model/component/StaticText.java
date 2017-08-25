package model.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.json.JSONArray;
import org.json.JSONObject;

public class StaticText extends Text implements ActionListener {

	private int index;
	private Random rand = new Random();
	private String text;
	private Timer t;

	public StaticText(JSONObject json) {
		super(json);

		JSONObject data = json.getJSONObject("data");
		verify(data, "text");
		verify(data, "random");
		verify(data, "delay");

		index = -1;

		t = new Timer(getDelay(), this);
		actionPerformed(null);
		t.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JSONArray array = json.getJSONObject("data").getJSONArray("text");
		if (array.length() == 0) {
			text = "";
		}
		boolean random = json.getJSONObject("data").getBoolean("random");
		if (random) {
			index = rand.nextInt(array.length());
		} else {
			index++;
			index %= array.length();
		}

		text = array.getString(index);
		t.setDelay(getDelay());
	}

	public int getDelay() {
		return json.getJSONObject("data").getInt("delay") * 1000;
	}

	@Override
	public String getText() {
		return text;
	}

}
