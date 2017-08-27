package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.MirrorModel;
import controller.operators.DebugOperation;
import controller.operators.ExitOperation;
import controller.operators.HelpOperation;
import controller.operators.ListOperation;
import controller.operators.Operation;
import controller.operators.SaveOperation;

public class MirrorController extends Thread {

	private static final String UNKNOWN = "Unknwon command";

	private List<Operation> operations = new ArrayList<>();

	public MirrorController(MirrorModel model) {
		operations.add(new HelpOperation(model, operations));
		operations.add(new ExitOperation(model));
		operations.add(new DebugOperation(model));
		operations.add(new SaveOperation(model));
		operations.add(new ListOperation(model));
		start();
	}

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			String input = scan.nextLine().trim();
			String[] row = input.split("\\s+", 2);
			String command = row[0];
			String data = input.substring(command.length()).trim();
			boolean found = false;
			for (Operation o : operations) {
				if (command.equals(o.getOperator())) {
					found = true;
					o.excetute(data);
				}
			}
			if (!found) {
				System.err.println(UNKNOWN);
			}
		}
		scan.close();
		System.out.println("Good bye!");
		System.exit(0);
	}
}
