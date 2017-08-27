package controller.operators;

import model.MirrorModel;

public class ListOperation extends Operation {

	public ListOperation(MirrorModel model) {
		super(model);
	}

	@Override
	public void excetute(String data) {
		for (String s : model.listComponents()) {
			System.out.println(s);
		}
	}

	@Override
	public String getOperator() {
		return "list";
	}

	@Override
	protected String helpText() {
		return "lists all components";
	}

}
