package controller;

import java.util.Arrays;
import java.util.Scanner;

import model.MirrorModel;

public class MirrorController extends Thread {

	private static final String UNKNOWN = "Unknwon command";

	private MirrorModel model;

	public MirrorController(MirrorModel model) {
		this.model = model;
		start();
	}

	@Override
	public void run() {
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			String[] row = scan.nextLine().trim().split("\\s+", 2);
			System.out.println(Arrays.toString(row));
			String command = row[0];
			switch (command) {
			case "debug":
				if (row.length == 1) {
					System.out.printf("debug: %s%n", model.isDebug());
				} else if (row[1].equalsIgnoreCase("true")) {
					model.setDebug(true);
				} else if (row[1].equalsIgnoreCase("false")) {
					model.setDebug(false);
				} else {
					System.err.println("debug [true/false]");
				}
				break;
			default:
				System.err.println(UNKNOWN);
			}
		}
		scan.close();
		System.out.println("Good bye!");
		System.exit(0);
	}
}
