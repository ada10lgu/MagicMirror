package mirror.controller.console;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mirror.controller.console.instruction.ExitInstruction;
import mirror.controller.console.instruction.Instruction;
import mirror.controller.console.instruction.ResourceInstructions;
import mirror.model.MirrorModel;

public class ConsoleController extends Thread {

	private List<Instruction> instructions;

	public ConsoleController(MirrorModel model) {
		instructions = new ArrayList<>();

		instructions.add(new ExitInstruction(model));
		instructions.add(new ResourceInstructions(model));
		
		start();
	}

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String inst = line.split("\\s+")[0];
			for (Instruction instruction : instructions) {
				if (instruction.hasOperator(inst)) {
					instruction.excetute(line);
					break;
				}
			}
		}
		scan.close();
		System.out.println("Closing system...");
		System.exit(0);
	}

}
