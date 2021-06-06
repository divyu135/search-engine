package searchengine;

import sorting.Sort;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

/**
 * Class to implement search engine
 * @author Divyajeet, Sam, Vyoma, Hussain
 *
 */
public class SearchEngine {

	/**
	 * @param keywords
	 * @return
	 */
	public static String makeKeywordFiles(String[] keywords) {
		// Making .dat file for each keyword
		String keywordFile = "";
		for (String str : keywords) {
			keywordFile = keywordFile + str + "_";
		}
		keywordFile = keywordFile + ".dat";
		return keywordFile;
	}

	public static boolean checkCacheFiles(String keywordFile) {
		boolean checkFile = false;

		File hashmapFolder = new File("./data/hashmaps");
		File[] hashFiles = hashmapFolder.listFiles();

		for (File file : hashFiles) {
			String myFileName = file.getName();
			if (myFileName.compareTo(keywordFile) == 0) {
				checkFile = true;
				break;
			}
		}
		return checkFile;
	}

	public static void displaySearchResult(HashMap<Integer, Integer> freqList, HashMap<Integer, String> urlIndex,
			HashMap<Integer, String> titleIndex, String search_text) {

		System.out.println("Top Ten Search Results for \"" + search_text + "\" are:\n");
		boolean nomatch = false;
		int j = 0;
		for (HashMap.Entry<Integer, Integer> entry : freqList.entrySet()) {
			if (j < 10) {
//				System.out.println(entry.getValue());
				// System.out.println(entry.getKey() + " = " + entry.getValue());
				if (entry.getValue() > 0) {
					int urlKey = entry.getKey();
					System.out.println(titleIndex.get(urlKey));
					System.out.println(urlIndex.get(urlKey));
					System.out.println("Number of Occurance: " + entry.getValue() + "\n");
				} else {
					nomatch = true;
					break;
				}
				j++;

			}
		}
		if (nomatch) {
			System.out.println("Your search- " + search_text + " - did not match any documents or found few results.");
			System.out.println("\nThinking about suggestions: ");
			EditDistance.suggestions(search_text);
		}

	}

	public static void searching(boolean fileExist, String[] keyWords, String search_text) {

		// Map each url(value) with index(value) in hashmap
		HashMap<Integer, String> urlIndex = new HashMap<Integer, String>();
		HashMap<Integer, String> titleIndex = new HashMap<Integer, String>();

		// Getting HashMap for searched keywords from hashmaps directory
		HashMap<Integer, Integer> freqList = new HashMap<Integer, Integer>();

		if (fileExist == true) {
			urlIndex = HashMapController.indexURLS();
			titleIndex = HashMapController.indexTitles();
			freqList = HashMapController.retreiveHashMap(keyWords);
		} else {
			urlIndex = HashMapController.indexURLS();
			titleIndex = HashMapController.indexTitles();
			freqList = HashMapController.getFreqList(keyWords);
			freqList = HashMapController.sortHashMap(freqList);
			HashMapController.storeHashMap(freqList, keyWords);
		}

		displaySearchResult(freqList, urlIndex, titleIndex, search_text);
	}

	public static void main(String[] args) throws IOException {

		System.out.println("\t\t  ACC Search Engine Project\n");
		System.out.println("\t\t\tDeveloped By: \n");
		System.out.println("Divyajeet\t\tVyoma\t\tHussain\t\tSam");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\nEnter: \n" + "[1] for Search Engine\n" + "[2] for Web Crawling and Parsing\n"
					+ "[3] for clearing caches\n" + "[0] to exit\n");


			int input = sc.nextInt();
			sc.nextLine();
			
			if (input == 0) {
				System.out.println("exit...");
				System.exit(0);
			} else if (input == 1) {
				// Scanner for reading user search input
				System.out.println("\nEnter Text to Search: ");
//				sc.next();
				String search_text;
				search_text = sc.next();

				// Converting input string to tokens
				// Considering only keywords i.e without stopwords
				String stopwords = "./data/default/stopwords.txt";
				String[] keyWords = Tokenizer.getKeywords(search_text, stopwords);

				// Sorting returned keywords
				Sort.mergeSort(keyWords);

				// making keyword files
				String keywordFile = makeKeywordFiles(keyWords);
				boolean exist = checkCacheFiles(keywordFile);

				searching(exist, keyWords, search_text);
			}
			if (input == 2) {
				try {
					WebCrawler.runCrawler();
					HtmlToText.convertHtmlToText(sc);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (input == 3) {
				File hashmaps = new File("./data/hashmaps");
				FileUtils.cleanDirectory(hashmaps);
				System.out.println("Caches Cleared....\n");
			} 
		}
	}

}
