package summarize;

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

public class Summarizer {

	Stemmer stemmer = null;
	
	// Article array
	String[] articles = null;
	
	int numberOfSentences = 0, totalSentences = 0, totalWords = 0, totalStopWords = 0;
	//ArrayList<String> sentences = new ArrayList<String>(); // used for output
	ArrayList<ArrayList<String>> docs = new ArrayList<ArrayList<String>>(); // split sentences (will hold arraylist of split sentences)
	//HashMap<String, Double> words= new HashMap<String, Double>(); // contain idf values as well
	//String[] words = new String[] {};
	ArrayList<String> stop = new ArrayList<String>();
	//ArrayList<Float> idf = new ArrayList<Float>();
	//HashMap<ArrayList<String>, ArrayList<Double>> ntf = new HashMap<ArrayList<String>, ArrayList<Double>>();

	HashMap<String, Integer> mostOccur = new HashMap<String, Integer>();
	
	
	String fileName = "";
	Scanner scan, keyboard;
	File inputFile, outputFile;
	String stemmed;
	
	
	//public static final String INPUT_FILE = "test.txt";
	//public static final String OUTPUT_FILE = "sumarize.txt";
	//public static final String STOPWORDS = "StopWords.txt";
	
	//Constructor
	Summarizer(String[] args) {
		// Create stemmer
		articles = args;
		
		//FileWriter fw = null;
		///PrintWriter pw = null;
		///FileOutputStream out = null;
		
		FileWriter fw = null;
		PrintWriter writer = null;
		
		// get file name
		/*keyboard = new Scanner(System.in);
		System.out.println("Enter the file name you would like to summarize (include the extension): ");
		inputFile = new File(keyboard.next());
		
		// get number of sentences for summary
		// should throw exception for non-int
		System.out.println("Enter number of sentences you would like in the summary: ");
		numberOfSentences = keyboard.nextInt();
		*/
		try {
			scan = new Scanner(new File("StopWords.txt"));
			
			while(scan.hasNext()) {
				stop.add(scan.next().trim());
				totalStopWords++;
			}
		
			// I don't think i need the following right away
			/*scan = new Scanner(inputFile);
			scan.useDelimiter("[.?!\"]");
			while(scan.hasNext()) {
				sentences.add(scan.next().trim());
				totalSentences++;
			}*/
			Stemmer stem = new Stemmer();
			stem.startStemming(articles);
			

			for(String article : articles) {
				String text = "";
				String[] words = null;
				ArrayList<String> temp = new ArrayList<>();
				
				scan.reset();
				scan = new Scanner(new File(article+".stem"));
				scan.useDelimiter("[.?!\"]");
				while (scan.hasNext()) {
					text = scan.nextLine();
					text = text.toLowerCase();
					//System.out.println(text);
					
					words = text.split("\\p{Blank}");
					for (int i = 0; i < words.length; i++) {
						words[i] = words[i].replaceAll("$|\\[.+?\\]|:|@|['].", "");
						words[i] = words[i].replaceAll("[.?!]$", "");
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
				
				temp.removeAll(stop);
				
				//fw = new FileWriter("temp.txt");
				
				//out = new FileOutputStream(new File("temp.txt"));
				//pw = new PrintWriter(out);
				
				fw = new FileWriter("temp.txt");
				writer = new PrintWriter(fw);
				
				//pw = new PrintWriter(fw);
				/*for(String word : temp) {
					String t = word;
					//out.write(word.getBytes());
					t = t.replaceAll("['].", "");
					t = t.replaceAll("[.]$", "");
					t = t.replaceAll(",", "");
					//System.out.println(word);
					//writer.write(wod + "\n");
					temp.add(word)
				}*/
				
				for (String word : temp) {
					System.out.println(word);
				}
			
			
				//temp = Stemmer.startStemming(article);
				
				docs.add(temp);
				
				
			
			}
			for(ArrayList<String> doc : docs) {
				for (String val : doc) {
					System.out.print(val + " ");
				}
				System.out.println();
			}
			//fw.close();
			//writer.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

		
		//System.out.println("Please be patient. The program is currently summarizing...");
		
		//removeStopWords();
			
		/*for(List<String> doc : docs) {
			for(String word : doc) {
				if(!words.containsKey(word)) {
				words.put(word, getIdf(word));
				//System.out.println(words);
				}
			}			           
		}
		
		int maxOccurence = 0;
		for (Entry<String, Double> word : words.entrySet()) {
			maxOccurence = 0;
			for (ArrayList<String> doc : docs) {
				
				for (String val : doc) {
					if(word.getKey().equalsIgnoreCase(val)) {
						//System.out.println(val + " " + word);
						maxOccurence++;
						//System.out.println(maxOccurence);
					}
				}
			}
			mostOccur.put(word.getKey(), maxOccurence);
		}
		
		for(Entry<String, Integer> max : mostOccur.entrySet()) {
			if(maxOccurence < max.getValue()) {
				maxOccurence = max.getValue();
			}
		}
		 
		// get word and idf value
		// multiply each idf value for the word * ntf of each entry with same word
		for (Entry<String, Double> word : words.entrySet()) {
			//System.out.println(maxOccurence);
			getNtf(word.getKey(), maxOccurence);
			//System.out.println(ntfValues);
		}
		
		System.out.println(docs);
		
		// ntf * idf
		// I put the idf * ntf values in the ntf HashMap.
		for (Entry<String, Double> set : words.entrySet()) {
			for (Entry<ArrayList<String>, ArrayList<Double>> ntfValues: ntf.entrySet()) {
				for (String val : ntfValues.getKey()) {
					if(val.equalsIgnoreCase(set.getKey())) {
						int index = ntfValues.getKey().indexOf(val);
						//System.out.println(ntfValues.getKey());
						//System.out.println(ntfValues.getValue());
						ntfValues.getValue().set(index, ntfValues.getValue().get(index) * set.getValue());
					}
				}
			}
		
		}
		
		outputSummary();
		
		System.out.println("The summary has been written to " + "\"" + OUTPUT_FILE + "\"");
		*/
		
	//}
	
	/*
	public void outputSummary() {
		try {
			File fout = new File(OUTPUT_FILE);
			FileOutputStream fos = new FileOutputStream(fout);
		 
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			// write most occuring sentences to file
			HashMap<ArrayList<String>, Double> temp = new HashMap<>();
			ArrayList<Double> totals = new ArrayList<>();
		
			double sum;
			int count = 0;
			
			for (Entry<ArrayList<String>, ArrayList<Double>> ent : ntf.entrySet()) {
				sum = 0.0;
				for (Double val : ent.getValue()) {
					sum = sum + val;
				}
				temp.put(ent.getKey(), sum);
				totals.add(sum);
			}
			
			Collections.sort(totals);
			Collections.reverse(totals);
			for (Double val : totals)
				for (Entry<ArrayList<String>, Double> ent : temp.entrySet()) {
					if(ent.getValue().equals(val)) {
						for(ArrayList<String> doc : docs) {
							if (ent.getKey().equals(doc)) {
								//System.out.println(sentences.get(docs.indexOf(doc))+".");
								String sent = sentences.get(docs.indexOf(doc))+".";
								//System.out.println(sent);
								bw.write(sent);
								bw.newLine();
								count++;
								if(count == numberOfSentences) {
									bw.flush();
									bw.close();
									return;
								}
							}
						}
					}
				}
			
			

				//System.out.println(val);
			
			/*for (Double val : temp.values()) {
				System.out.println(val);
			}*/
			
	/*		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public double getIdf(String term) {
		//System.out.println(docs.size());
		int docsWithValue = 0;
		for(List<String> doc : docs) {
			for(String word : doc) {
				//System.out.println(word);
				if (word.trim().equalsIgnoreCase(term.trim())) {
					docsWithValue++;
					//System.out.println(docsWithValue);
					break;
				}
			}
		}
		
		return getLog(docs.size() / docsWithValue) + 1;
		
	}
	
	//ntf
	public void getNtf(String val, int max) {
		double result = 0.0;
		
		for(ArrayList<String> doc : docs) {
			
			// sets each word to 1 occurence per doc
			//ArrayList<String> temp = new ArrayList<String>();
			ArrayList<Double> ntfValues = new ArrayList<Double>();
			//ntfValues.clear();
			
			for (String word : doc) {
				if (val.equalsIgnoreCase(word)) {
						result++;		
				}
				/*if (!temp.contains(word)) {
					temp.add(word);
				}*/
	/*			//System.out.println(result/max);
				ntfValues.add(result / max);
				//System.out.println(max);
			}		
			//System.out.println(doc);
			//System.out.println(ntfValues);
			//System.out.println(doc);
			//System.out.println(ntfValues);
			ntf.put(doc, ntfValues);
			//System.out.println(ntf);
			//System.out.println(ntf);
		}
		
	}
	
	// Code snippet taken from TechieDelight Site 
	public static double getLog(double x) {
		return (Math.log(x) / Math.log(2) + 1);
	}
	*/
	// Used Geeks for Geeks for some
	// also inserts values into arrays and doc arraylist
	/*public void removeStopWords() {
		for (String article : articles) {
		scan = new Scanner(article);
		scan.useDelimiter(".");
		while(scan.hasNext()) {
			String sentence = scan.nextLine();
			System.out.println(sentence);
			
			String[] t = sentence.split("\\p{Blank}");
			
			//ArrayList<String> temp = new ArrayList<String>();
		
			for(String word : t) {
				word = word.trim();
				if(word.matches("")) {
					word = "";
				}
				else {
					
				}
				//System.out.println(word);
				//System.out.println(word);
			}
			
			//docs.add(temp);
			//System.out.println(docs.get(0));
		}
		}
		
					//stemmed = sentence;
			
					
					
				
			}
		
		for (ArrayList<String> doc : docs) {
			for(String word : doc) {
				
			}
			doc.removeAll(stop);
		}			

		//System.out.println(docs);
		
	}*/
	
	

 