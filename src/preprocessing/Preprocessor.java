package preprocessing;

/** Created by Chris Pajtas **/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import stemmer.Stemmer;

public class Preprocessor {

	Stemmer stemmer = null;
	
	// Article array
	String[] articles = null;
	
	int numberOfSentences = 0, totalSentences = 0, totalWords = 0, totalStopWords = 0;
	ArrayList<ArrayList<String>> docs = new ArrayList<ArrayList<String>>(); // split sentences (will hold arraylist of split sentences)
	ArrayList<String> stop = new ArrayList<String>();
	HashMap<String, Integer> mostOccur = new HashMap<String, Integer>();
	
	
	String fileName = "";
	Scanner scan, keyboard;
	File inputFile, outputFile;
	String stemmed;
	
	FileWriter fw = null;
	PrintWriter writer = null;
	
	//Constructor
	public Preprocessor(String[] args) {
		// Create stemmer
		articles = args;
	}
	
	public ArrayList<ArrayList<String>> preprocess() {
		
		try {
			scan = new Scanner(new File("StopWords.txt"));
			
			while(scan.hasNext()) {
				stop.add(scan.next().trim());
				totalStopWords++;
			}
		
			Stemmer stem = new Stemmer();
			stem.startStemming(articles);
			

			for(String article : articles) {
				String text = "";
				String[] words = null;
				ArrayList<String> temp = new ArrayList<>();
				
				scan.reset();
				scan = new Scanner(new File(article+".stem"));
				//scan.useDelimiter("[.?!\"]");
				while (scan.hasNext()) {
					text = scan.nextLine();
					text = text.toLowerCase();
					//System.out.println(text);
					
					words = text.split("\\p{Blank}");
					for (int i = 0; i < words.length; i++) {
						words[i] = words[i].replaceAll("$|\\[.+?\\]|:|@|['].", "");
						words[i] = words[i].replaceAll("[.?!]$", "");
						words[i] = words[i].replaceAll(".+?\\/.+?", "");
						words[i] = words[i].replaceAll(",|.+?'.+?$", "");
						words[i] = words[i].replaceAll("[\"]", "");
						words[i] = words[i].replaceAll("[\\d\\.]", "");
						words[i] = words[i].replaceAll("^\\(.+?\\)", "");
						words[i] = words[i].replaceAll("-", "").trim();
						
						
					}
					//System.out.println(words);
					
					temp.addAll(Arrays.asList(words));
					//System.out.println();
				}
				
				temp.removeAll(Arrays.asList(null,""));
				temp.removeAll(stop);
				
				fw = new FileWriter("temp.txt");
				writer = new PrintWriter(fw);
				
				docs.add(temp);
		
			}
			/*for(ArrayList<String> doc : docs) {
				for (String val : doc) {
					System.out.print(val + " ");
				}
				System.out.println();
			}	*/		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return docs;
	}
}
