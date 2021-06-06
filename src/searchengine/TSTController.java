package searchengine;

import java.util.StringTokenizer;
import textprocessing.In;
import textprocessing.TST;

public class TSTController {
	
	/**
	 * This method is responsible for creating TST of each text file
	 * 
	 * @param finalPath
	 * @return TST
	 */
	public static TST<Integer> getTST(String finalPath) {
		TST<Integer> tst = new TST<Integer>();
		In in = new In(finalPath);

		while (!in.isEmpty()) {
			String text = in.readLine();

				StringTokenizer st = new StringTokenizer(text, " ");
				while (st.hasMoreTokens()) {
					String word = st.nextToken();
					word = word.toLowerCase();
					HashMapController.set.add(word);

					if (tst.contains(word)) {

						tst.put(word, tst.get(word) + 1);
					} else {
						tst.put(word, 1);
					}
				}
		}
		return tst;
	}
	
}

	