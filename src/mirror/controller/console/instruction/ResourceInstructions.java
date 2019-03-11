package mirror.controller.console.instruction;

import java.util.List;

import mirror.model.MirrorModel;
import mirror.model.resource.Resource;

public class ResourceInstructions extends Instruction {

	public ResourceInstructions(MirrorModel model) {
		super(model);
	}

	@Override
	public void execute(String instruction, String load, String[] data) {
		if (load.isEmpty()) {
			int i = 0;
			for (Resource r : model.getResources()) {
				System.out.printf("%d: %s (%s)\n", i, r.getInfo(), r.getType());
				i++;
			}
			return;
		}

		int i = 0;
		try {
			i = Integer.parseInt(data[1]);
		} catch (NumberFormatException e) {
			System.out.println("Invalid index");
			return;
		}

		Resource r = model.getResources().get(i);

		if (data.length == 2) {
			List<String> actions = r.getActions();
			if (actions.isEmpty()) {
				System.out.println("No actions exists for the resource.");
			} else {
				System.out.println("The following actions are available:");
				for (String s : actions) {
					System.out.printf(" %s\n", s);
				}
			}
			return;
		}

		r.performAction(load.split("\\s+", 2)[1]);

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
