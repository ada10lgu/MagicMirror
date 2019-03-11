package mirror;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import mirror.controller.LoginController;
import mirror.controller.console.ConsoleController;
import mirror.gui.ImageLoader;
import mirror.gui.MirrorGUI;
import mirror.model.Config;
import mirror.model.MirrorModel;

public class MagicMirror {

	public static void main(String[] args) {
		String file = "settings.json";

		Config config = new Config(file);

		try {
			config.load();

			MirrorModel model = new MirrorModel(config);
			LoginController loginController = new LoginController(config);
			if (model.login(loginController)) {
				System.out.printf("Connected to %s\n", config.getServerSettings().getString("host"));
			} else {
				System.err.println("Error logging in!");
				System.exit(3);
			}

			model.loadResources();

			ImageLoader imageLoader = new ImageLoader(config);
			imageLoader.load();

			new ConsoleController(model);
			
			new MirrorGUI(config, model, imageLoader);

			config.save();
		} catch (IOException e) {
			System.err.printf("Could not load config file '%s'", file);
			System.exit(1);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Algoritm needed for login not available");
			System.exit(2);
		}

	}

}
