import java.io.File;
import java.io.FileNotFoundException;

import model.MirrorModel;

public class MagicMirror {
	public static void main(String[] args) {

		try {
			File json = new File("settings.json");

			if (args.length > 0) {
				json = new File(args[0]);
			}

			MirrorModel model = new MirrorModel(json);
		} catch (FileNotFoundException e) {
			System.err.println("Could not load config file!");
			System.exit(1);
		}

	}
}
