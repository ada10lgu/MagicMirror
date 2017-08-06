package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class MirrorModel {

	private ArrayList<Component> components;
	private File json;


	public MirrorModel(File json) throws FileNotFoundException {
		this.json = json;
		
		System.out.println("Magic mirror loaded");
		load();	
				
	}
	
	public void load() throws FileNotFoundException {
		System.out.print("Reading conifg...");
		Scanner s = new Scanner(json);
		StringBuilder sb = new StringBuilder();
		while (s.hasNext()) {
			sb.append(s.nextLine());
		}
		s.close();
		
		ArrayList<Component> components = new ArrayList<>();
		
		for (Object o : new JSONArray(sb.toString())) {
			components.add(new Component((JSONObject) o));
		}
		
		this.components = components;
		System.out.println("done!");
				
	}
}
