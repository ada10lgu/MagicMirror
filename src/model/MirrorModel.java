package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.component.Component;

public class MirrorModel extends Observable {

	private File settings;
	private TreeMap<String, Component> tree = new TreeMap<>();
	private boolean debug;

	public MirrorModel(File settings, boolean debug) throws FileNotFoundException {
		this.settings = settings;
		this.debug = debug;
		System.out.println("Loading components");
		load();
		System.out.printf(" %d components read%n", tree.size());
	}

	public synchronized boolean isDebug() {
		return debug;
	}

	public synchronized void setDebug(boolean debug) {
		this.debug = debug;
	}

	public synchronized List<Component> getComponents() {
		ArrayList<Component> components = new ArrayList<>();
		for (String key : tree.keySet()) {
			components.add(tree.get(key));
		}
		return components;
	}

	public synchronized void load() throws FileNotFoundException {
		HashMap<String, Integer> types = new HashMap<>();
		TreeMap<String, Component> tree = new TreeMap<>();
		JSONArray array = read();
		for (Object object : array) {
			if (object instanceof JSONObject) {
				JSONObject json = (JSONObject) object;
				Component component = Component.create(json);
				String type = component.getType();
				Integer index = types.get(type);
				if (index == null)
					index = 0;
				String name = String.format("%s-%d", type, index);
				types.put(type, ++index);
				tree.put(name, component);
			} else {
				throw new JSONException("Wrong structure, requires array of object");
			}
		}
		this.tree = tree;
	}

	private JSONArray read() throws FileNotFoundException {
		Scanner s = new Scanner(settings);
		StringBuilder sb = new StringBuilder();
		while (s.hasNext()) {
			sb.append(s.nextLine()).append("\n");
		}
		s.close();
		return new JSONArray(sb.toString());
	}

	public void save() throws IOException {
		JSONArray array = new JSONArray();
		for (String key : tree.keySet()) {
			JSONObject json = tree.get(key).getJSON();
			array.put(json);
		}
		FileWriter fw = new FileWriter(settings);
		fw.write(array.toString(4));
		fw.flush();
		fw.close();
	}

	public List<String> listComponents() {
		return new ArrayList<>(tree.keySet());
	}

}
