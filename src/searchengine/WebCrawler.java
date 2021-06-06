package searchengine;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import textprocessing.In;

public class WebCrawler {
	private HashSet<String> links;
	ArrayList<String> arr = new ArrayList<String>();
	public WebCrawler() {
		links = new HashSet<String>();
	}

	public void get_page_links(String myURL) {
		// The below loop check if the URLs are already crawled
		if (!links.contains(myURL)) {
			try {
				Document document = Jsoup.connect(myURL).get();
				String title = document.title();
				FileWriter fw1 = new FileWriter("./data/default/websites.txt",true); //the true will append the new data
				FileWriter fw2 = new FileWriter("./data/default/titles.txt",true); //the true will append the new data
				
				Elements link_on_page =document.select(".mw-parser-output").select("a[href]");
				for (Element page : link_on_page) {
					myURL=page.attr("abs:href");
					title = page.attr("title");
					if (links.add(myURL)) {
						
//						System.out.print(title+" ");
//						System.out.println(myURL);
						if(title!="") {
							fw1.write(myURL+"\n");//appends the string to the file
						    fw2.write(title+"\n");						
							arr.add(myURL);
							System.out.println(myURL);
							int i = arr.indexOf(myURL);
							String myString = Integer.toString(i);
							String str = myURL;	
							saveUrl(myString, str);
						}
					}
				}
				fw1.close();
				fw2.close();
			} catch (IOException e) {
				System.err.println("For '" + myURL + "': " + e.getMessage());
			}

		}
	}

	public void saveUrl(final String filename, final String urlString) throws MalformedURLException, IOException {
		{
			try {
				URL url = new URL(urlString);
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

				// Enter filename in which you want to download
				String str = filename + ".html";

				BufferedWriter writer = new BufferedWriter(
						new FileWriter("data/HTMLFiles/" + str));

				// read each line from stream till end
				String line;
				while ((line = reader.readLine()) != null) {
					writer.write(line);
				}

				reader.close();
				writer.close();
//				System.out.println("Successfully Downloaded.");
			}

			// Exceptions
			catch (MalformedURLException exception) {
				System.out.println("URL Exception raised");
			} catch (IOException ioe) {
				System.out.println("IOException raised");
			}
		}
	}

	public static void runCrawler() {
	 // Pick a URL from the frontier
//		Scanner s = new Scanner(System.in);
//		System.out.println("Enter the URL: ");
//		String url = s.nextLine();
		WebCrawler w = new WebCrawler();
		System.out.println("Getting articles from Wikipedia's Index of Computing Articles....");
		w.get_page_links("https://en.wikipedia.org/wiki/Index_of_computing_articles");
		System.out.println("Getting articles from Wikipedia's Algorithms and Data Structure topic list....");
		w.get_page_links("https://en.wikipedia.org/wiki/List_of_terms_relating_to_algorithms_and_data_structures");
		System.out.println("Getting articles from Wikipedia's Index of Branches of Science....");
		w.get_page_links("https://en.wikipedia.org/wiki/Index_of_branches_of_science");
		System.out.println("Getting articles from Wikipedia's List of AI projects....");
		w.get_page_links("https://en.wikipedia.org/wiki/List_of_artificial_intelligence_projects");		
	}
}
