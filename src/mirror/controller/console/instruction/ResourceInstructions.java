package mirror.controller.console.instruction;

import mirror.model.MirrorModel;
import mirror.model.resource.Resource;

public class ResourceInstructions extends Instruction {

	public ResourceInstructions(MirrorModel model) {
		super(model);
	}

	@Override
	public void execute(String instruction, String load, String[] data) {
		if (load.isEmpty()) {
			printComponents();
		}
	}

	private void printComponents() {
		int i = 0;
		for (Resource r : model.getResources()) {
			System.out.printf("%d: %s (%s)\n", i, r.getInfo(), r.getType());
			i++;
		}
	}

	@Override
	public String[] getOperator() {
		return new String[] { "resource", "r" };
	}

	@Override
	protected String helpText() {
		return "instruction [index] [action]";
	}

}
