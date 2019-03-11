package mirror.controller.console.instruction;

import mirror.model.MirrorModel;

public abstract class Instruction {

	protected MirrorModel model;

	public Instruction(MirrorModel model) {
		this.model = model;
	}

	public final void excetute(String row) {
		String[] data = row.split("\\s+");
		String instruction = data[0];
		String load = data.length > 1 ? row.split("\\s+", 2)[1] : "";
		execute(instruction, load, data);
	}

	public abstract void execute(String instruction, String load, String[] data);

	public abstract String[] getOperator();

	public boolean hasOperator(String operator) {
		for (String s : getOperator()) {
			if (s.equalsIgnoreCase(operator))
				return true;
		}
		return false;
	}

	protected abstract String helpText();

	@Override
	public final String toString() {
		return getOperator() + "\t" + helpText();
	}

}
