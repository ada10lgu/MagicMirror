package controller.operators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.MirrorModel;

public class HelpOperation extends Operation {

	private List<Operation> operations;

	public HelpOperation(MirrorModel model, List<Operation> operators) {
		super(model);
		this.operations = operators;
	}

	@Override
	public void excetute(String data) {
		List<Operation> operations = new ArrayList<>(this.operations);
		Collections.sort(operations);
		for (Operation o : operations) {
			System.out.println(o);
		}
	}

	@Override
	public String getOperator() {
		return "help";
	}

	@Override
	protected String helpText() {
		return "Displays this help text";
	}

}
