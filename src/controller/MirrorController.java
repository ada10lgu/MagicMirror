package controller;

import java.io.IOException;
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
		loop: while (scan.hasNext()) {
			String[] row = scan.nextLine().trim().split("\\s+", 2);
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
			case "save":
				System.out.print("Saving to file...");
				try {
					model.save();
				} catch (IOException e) {
					System.err.println("failed");
				}
				System.out.println("saved!");
				break;
			case "help":
				System.out.println("debug [true/false] \tchange debug state");
				System.out.println("help \t\t\tdisplays this text");
				System.out.println("exit \t\t\texit the program");
				System.out.println("save \t\t\tsaves the config to file");
				break;
			case "exit":
				break loop;
			default:
				System.err.println(UNKNOWN);
			}
		}
		scan.close();
		System.out.println("Good bye!");
		System.exit(0);
	}
}
