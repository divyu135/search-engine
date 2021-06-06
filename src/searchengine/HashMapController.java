package searchengine;

import static java.util.stream.Collectors.toMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import sorting.Sort;
import textprocessing.In;
import textprocessing.TST;
//import textprocessing.TST;

public class HashMapController {
	
	static HashSet<String> set = new HashSet<String>();

	/**
	 * This methods is responsible for indexing URLs by fetching URLs from file and
	 * inserting each URL into Hashmap
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> indexURLS() {
		int i = 0;
		HashMap<Integer, String> UrlIndex = new HashMap<Integer, String>();
		In in = new In("./data/default/websites.txt");

		while (!in.isEmpty()) {

			String text = in.readLine();
			UrlIndex.put(i, text);
			i++;
		}
		return UrlIndex;
	}
	
	/**
	 * This methods is responsible for indexing titles by fetching titles from file and
	 * inserting each titles into Hashmap
	 * 
	 * @return
	 */
	public static HashMap<Integer, String> indexTitles() {
		int i = 0;
		HashMap<Integer, String> titles = new HashMap<Integer, String>();
		In in = new In("./data/default/titles.txt");

		while (!in.isEmpty()) {

			String text = in.readLine();
			titles.put(i, text);
			i++;
		}
		return titles;
	}


	/**
	 * This method is responsible to find the the occurrence of the keywords in each
	 * text file and get the count
	 * 
	 * @param keyWords
	 * @return
	 */
	public static HashMap<Integer, Integer> getFreqList(String[] keyWords) {

		// Map each text file to its corresponding number into an arraylist
		ArrayList<String> textList = new ArrayList<>();
		HashMap<Integer, Integer> freqList = new HashMap<Integer, Integer>();

		File folder = new File("./data/parsedURL/");
		File[] files = folder.listFiles();
		
		for (File file : files) {
			String myURL = file.getName();
			textList.add(myURL);
		}

		for (int i = 0; i < textList.size(); i++) {

			String filePath = "./data/parsedURL/";
			String fileName = textList.get(i);
			String finalPath = filePath + fileName;

			String tempFileIndex = fileName.substring(0, (fileName.length() - 4));
			int fileIndex = Integer.parseInt(tempFileIndex);
			// System.out.println(fileIndex);

			TST<Integer> tst = new TST<Integer>();
			tst = TSTController.getTST(finalPath);
			int counter = 0;

			for (String str : keyWords) {
				if (tst.contains(str)) {
					int count = tst.get(str);
					counter = counter + count;
				}
			}

			freqList.put(fileIndex, counter);
		}

		return freqList;
	}
	
	public static void storeHashSet() {

		String filePath = "./data/default/all_keys.dat";
		
		try {

			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(set);
			out.close();
			fileOut.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * This method is responsible to sort hashmap in descending order based on the
	 * values
	 * 
	 * @param freqList
	 * @return
	 */
	public static HashMap<Integer, Integer> sortHashMap(HashMap<Integer, Integer> freqList) {
		HashMap<Integer, Integer> sortedFreqList = freqList.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

		return sortedFreqList;
	}
	


	/**
	 * This method is used to store the frequency list hashmap used for Page Ranking
	 * 
	 * @param freqList
	 * @param keyWords
	 */
	public static void storeHashMap(HashMap<Integer, Integer> freqList, String[] keyWords) {

		Sort.mergeSort(keyWords);
		String fileName = "";

		for (String str : keyWords) {

			fileName = fileName + str + "_";
		}

		fileName = fileName + ".dat";

		// System.out.println(fileName);

		String filePath = "./data/hashmaps/";
		String finalPath = filePath + fileName;

		try {

			FileOutputStream fileOut = new FileOutputStream(finalPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(freqList);
			out.close();
			fileOut.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	

	/**
	 * This method is used to retrieve the frequency list hashmap used for Page
	 * Ranking
	 * 
	 * @param keyWords
	 * @return
	 */
	public static HashMap<Integer, Integer> retreiveHashMap(String[] keyWords) {

		Sort.mergeSort(keyWords);

		String fileName = "";

		for (String str : keyWords) {

			fileName = fileName + str + "_";
		}

		fileName = fileName + ".dat";
		String filePath = "./data/hashmaps/";
		String finalPath = filePath + fileName;

		HashMap<Integer, Integer> freqList = new HashMap<Integer, Integer>();
		freqList = null;

		try {

			FileInputStream fileIn = new FileInputStream(finalPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			freqList = (HashMap<Integer, Integer>) in.readObject();
			in.close();
			fileIn.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return freqList;

	}
}
