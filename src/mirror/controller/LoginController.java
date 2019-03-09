package mirror.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.json.JSONObject;

import mirror.model.Config;

public class LoginController {

	private JSONObject data;

	public LoginController(Config config) {
		data = config.getServerSettings();
	}

	public String login(String challenge) throws NoSuchAlgorithmException {
		System.out.printf("Enter password for user %s:", data.getString("username"));
		Scanner scanner = new Scanner(System.in);
		String password = scanner.next();
		scanner.close();
		String hash = sha256(password);
		hash = sha256(hash + challenge);
		return hash;

	}

	private String sha256(String data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
