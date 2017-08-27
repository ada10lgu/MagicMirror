package controller.operators;

import model.MirrorModel;

public class ExitOperation extends Operation {

	public ExitOperation(MirrorModel model) {
		super(model);
	}

	@Override
	public void excetute(String data) {
		System.out.println("Good bye!");
		System.exit(0);
	}

	@Override
	public String helpText() {
		return "quits the program";
	}

	@Override
	public String getOperator() {
		return "exit";
	}

}
