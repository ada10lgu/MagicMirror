package controller.operators;

import model.MirrorModel;

public abstract class Operation implements Comparable<Operation> {

	protected MirrorModel model;

	public Operation(MirrorModel model) {
		this.model = model;
	}

	public abstract void excetute(String data);

	public abstract String getOperator();

	protected abstract String helpText();

	@Override
	public final String toString() {
		return getOperator() + "\t" + helpText();
	}

	@Override
	public int compareTo(Operation operator) {
		return getOperator().compareTo(operator.getOperator());
	}

}
