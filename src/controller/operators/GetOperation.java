package controller.operators;

import model.MirrorModel;

public class GetOperation extends Operation {

	public GetOperation(MirrorModel model) {
		super(model);
	}

	@Override
	public void excetute(String data) {
	}

	@Override
	public String getOperator() {
		return null;
	}

	@Override
	protected String helpText() {
		return null;
	}
}
