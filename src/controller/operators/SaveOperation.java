package controller.operators;

import java.io.IOException;

import model.MirrorModel;

public class SaveOperation extends Operation {

	public SaveOperation(MirrorModel model) {
		super(model);
	}

	@Override
	public void excetute(String data) {
		try {
			System.out.print("Saving...");
			model.save();
			System.out.println("saved");
		} catch (IOException e) {
			System.err.println("Could not save.");
		}
	}

	@Override
	public String getOperator() {
		return "save";
	}

	@Override
	protected String helpText() {
		return "save settings to file";
	}

}
