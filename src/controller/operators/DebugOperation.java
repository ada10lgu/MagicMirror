package controller.operators;

import model.MirrorModel;

public class DebugOperation extends Operation {

	public DebugOperation(MirrorModel model) {
		super(model);
	}

	@Override
	public void excetute(String data) {
		if (data.isEmpty()) {
			System.out.println(model.isDebug());
		} else if (data.equalsIgnoreCase("true")) {
			model.setDebug(true);
		} else if (data.equalsIgnoreCase("false")) {
			model.setDebug(false);
		} else {
			System.err.println("Illegal value, use true or false");
		}
	}

	@Override
	public String getOperator() {
		return "debug";
	}

	@Override
	protected String helpText() {
		return "disable or enable debug mode (true/false)";
	}

}
