package searchengine;

import java.io.*;
import java.util.Scanner;

import org.jsoup.Jsoup;

public class HtmlToText {
	// This method converts HTML Files into text documents.
	public static void convertHtmlToText(Scanner sc) throws IOException, FileNotFoundException, NullPointerException {
		
		String src = "./data/HTMLFiles/";
		String dest = "./data/parsedURL/";
		
		System.out.println("Reading files from ./data/HTMLFiles/: ");
		org.jsoup.nodes.Document my_doc = null;
		BufferedWriter my_out = null;

		try {
			File dir = new File(src);
			File[] file_Array = dir.listFiles();
			int i = 0;
			for (File file : file_Array) {
				my_doc = Jsoup.parse(file, "UTF-8");
				String my_str = file.getName().substring(0, file.getName().lastIndexOf('.'));
				my_out = new BufferedWriter(new FileWriter(dest + my_str + ".txt"));
				my_out.write(my_doc.text());
				my_out.close();
			}

		} catch (Exception e) {
			System.out.println("Folder location not found");
		}
		
		System.out.println("\nParsed files stored in: ./data/parsedURL/");
		

	}

}