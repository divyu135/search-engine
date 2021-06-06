package searchengine;

import java.util.StringTokenizer;
import textprocessing.In;

public class Tokenizer {

	/**
	 * This method is responsible for removing stop words from the input keywords
	 * 
	 * @param inputString
	 * @return String array of keywords
	 */
	public static String[] getKeywords(String inputString, String stopWordsFile) {
		int i = 0;
		In in = new In(stopWordsFile);
		inputString = inputString.toLowerCase();

		while (!in.isEmpty()) {
			String text = in.readLine();
			text = text.toLowerCase();
			text = "\\b" + text + "\\b";
			inputString = inputString.replaceAll(text, " ");
		}

		StringTokenizer st = new StringTokenizer(inputString, " ");
		String[] keyWords = new String[st.countTokens()];

		while (st.hasMoreTokens()) {
			keyWords[i] = st.nextToken();
			i++;
		}
		return keyWords;
	}

}
