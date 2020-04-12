package xyz.disarray.game.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MessageGenerator {
	
	/**
	 * Selects a message at random from the messages text file to be displayed on the window's title.
	 * 
	 * @return Returns a random message from the messages text file.
	 */
	public static String generate() {
		File file = new File("data/messages");
		Scanner sc;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			return "";
		}

		ArrayList<String> messages = new ArrayList<>();

		while (sc.hasNextLine())
			messages.add(sc.nextLine());

		sc.close();

		return messages.get((int) (Math.random() * messages.size()));
	}
}
