package general;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class NotesTool {

	public static void main(String[] args) {
		String text = "This is a test. Test of word frequency. The word test should have the highest frequency in this test.";

		Map<String, Integer> freq = wordFreq(text);

		for (Entry<String, Integer> entry : freq.entrySet())
			System.out.println(entry.getKey() + " -> " + entry.getValue());

	}

	public static Map<String, Integer> wordFreq(String text) {
		Map<String, Integer> words = new HashMap<String, Integer>();

		// TODO: Deal with other characters like ! and ?
		for (String sentence : text.split("\\.")) {
			for (String word : sentence.split(" ")) {
				words.putIfAbsent(word.toLowerCase(), 0);
				words.put(word.toLowerCase(), words.get(word.toLowerCase()) + 1);
			}
		}

		return words;
	}

}
