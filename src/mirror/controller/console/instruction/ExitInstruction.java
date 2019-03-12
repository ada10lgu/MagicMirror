package mirror.controller.console.instruction;

import mirror.model.MirrorModel;

public class ExitInstruction extends Instruction {

	public ExitInstruction(MirrorModel model) {
		super(model);
	}

	@Override
	public void execute(String instruction, String load, String[] data) {
		System.out.println("Exiting program!");
		System.exit(0);
	}

	@Override
	public String[] getOperator() {
		return new String[] { "quit", "exit", "stop", "q" };
	}

	@Override
	protected String helpText() {
		return "Quits the program";
	}

}
