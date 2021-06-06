package searchengine;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

public class EditDistance {
	static Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();
	static ArrayList<String> key = new ArrayList<String>();

	//Using Regular Expressions for pattern matching
		public static void suggestions(String pattern) {
				findWord(pattern);
				
				Integer value = 1;
				Integer val = 0;
				int counter = 0;
				System.out.println("\nDid you mean?:");
				System.out.println("****************");
				for (Map.Entry entry : numbers.entrySet()) {
					if (val == entry.getValue()) {
						break;
					} else {
						if (value == entry.getValue()) {
							if (counter == 0) {
								System.out.print(entry.getKey());
								counter++;
							}

							else {
								System.out.print(" or " + entry.getKey());
								counter++;
							}

						}

					}
				}
				System.out.println();
		}
		
		public static HashSet<String> retreiveHashSet() {

			String filePath = "./data/default/all_keys.dat";
			HashSet<String> set = new HashSet<String>();
			set = null;

			try {
				FileInputStream fileIn = new FileInputStream(filePath);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				set = (HashSet<String>) in.readObject();
				in.close();
				fileIn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return set;
		}
		
		public static void findWord(String str) {
				for (String key : retreiveHashSet()) {
//					System.out.println(key);
					numbers.put(key, editDistance(str.toLowerCase(), key.toLowerCase()));
				}
		}

		public static int editDistance(String str1, String str2) {
			int len1 = str1.length();
			int len2 = str2.length();

			int[][] my_array = new int[len1 + 1][len2 + 1];

			for (int i = 0; i <= len1; i++) {
				my_array[i][0] = i;
			}

			for (int j = 0; j <= len2; j++) {
				my_array[0][j] = j;
			}

			// iterate though, and check last char
			for (int i = 0; i < len1; i++) {
				char c1 = str1.charAt(i);
				for (int j = 0; j < len2; j++) {
					char c2 = str2.charAt(j);

					if (c1 == c2) {
						my_array[i + 1][j + 1] = my_array[i][j];
					} else {
						int replace = my_array[i][j] + 1;
						int insert = my_array[i][j + 1] + 1;
						int delete = my_array[i + 1][j] + 1;

						int min = replace > insert ? insert : replace;
						min = delete > min ? min : delete;
						my_array[i + 1][j + 1] = min;
					}
				}
			}

			return my_array[len1][len2];
		}
}
