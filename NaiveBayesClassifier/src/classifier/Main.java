package classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws Exception {
		
		ArrayList<String> masterWordList = new ArrayList<String>();
	    ArrayList<String> fakeWordList = new ArrayList<String>();
	    ArrayList<String> trueWordList = new ArrayList<String>();
	    Classifier c = new Classifier();
	    Map<String, Float> fakeWordCountHashMap = new HashMap<String, Float>();
	    Map<String, Float> trueWordCountHashMap = new HashMap<String, Float>();
	    Map<String, Float> sumFakeTrueOfWordHashMap = new HashMap<String, Float>();
	    Map<String, Float> probWordHashMap = new HashMap<String, Float>();
	    Map<String, Float> probWordFakeHashMap = new HashMap<String, Float>();
	    Map<String, Float> probWordTrueHashMap = new HashMap<String, Float>();
	    int totWords = 0;
	    
	    // Let's say for now there are a total of two documents, 1 fake and 1 true.
	    // This makes P(fake) = .5 and P(true) = .5
	    // We can hard code the number of fake/true docs for simplicity
	    // The words in the docs are as follows:
	   
	    fakeWordList.add("fake");
	    fakeWordList.add("great");
	    fakeWordList.add("great");
	    fakeWordList.add("great");
	    fakeWordList.add("fake");
	    fakeWordList.add("tree");
	    fakeWordList.add("fake");
	    fakeWordList.add("fake");
	    fakeWordList.add("bake");
	    fakeWordList.add("true");
	    fakeWordList.add("true");
	    fakeWordList.add("ball");
	    
	    trueWordList.add("true");
	    trueWordList.add("true");
	    trueWordList.add("true");
	    trueWordList.add("bake");
	    trueWordList.add("fake");
	    trueWordList.add("bake");
	    trueWordList.add("bake");
	    trueWordList.add("great");
	    trueWordList.add("great");
	    trueWordList.add("great");
	    trueWordList.add("house");
	    trueWordList.add("house");
	    trueWordList.add("tree");
	    trueWordList.add("tree");
	    
	    masterWordList = c.createMasterList(fakeWordList, trueWordList);
	    
	    System.out.println("Master word list with duplicates: ");
	    for(int i=0; i < masterWordList.size(); i++) {
	    	System.out.print(masterWordList.get(i) + " ");
	    }
	    System.out.println();
	    
	    // remove duplicates
	    masterWordList = c.removeDuplicates(masterWordList);
	    System.out.println();
	    
	    System.out.println("Master word list without duplicates: ");
	    for(int i=0; i < masterWordList.size(); i++) {
	    	System.out.print(masterWordList.get(i) + " ");
	    }
	    System.out.println();
	    System.out.println();
	    
	    for(int i=0; i < masterWordList.size(); i++) {
	    	fakeWordCountHashMap.put(masterWordList.get(i), c.wordCount(masterWordList.get(i), fakeWordList));
	    }
	    
	    for(int i=0; i < masterWordList.size(); i++) {
	    	trueWordCountHashMap.put(masterWordList.get(i), c.wordCount(masterWordList.get(i), trueWordList));
	    }
	    
	    System.out.println("fakeWordList size (totalFakeWords)-> " + fakeWordList.size());
	    System.out.println("trueWordList size (totalTrueWords)-> " + trueWordList.size());
	    totWords = fakeWordList.size() + trueWordList.size();
	    System.out.println("total number of words--------------> " + totWords);
	    
	    for(int i=0; i < masterWordList.size(); i++) {
	    	sumFakeTrueOfWordHashMap.put(masterWordList.get(i), (fakeWordCountHashMap.get(masterWordList.get(i)) + 
	    									trueWordCountHashMap.get(masterWordList.get(i))));
	    }
	    
	    System.out.println("fakeWordCountHashMap-----> " + fakeWordCountHashMap);
	    System.out.println("trueWordCountHashMap-----> " + trueWordCountHashMap);
	    System.out.println("sumFakeTrueOfWordHashMap-> " + sumFakeTrueOfWordHashMap);
	    
	    for(int i=0; i < masterWordList.size(); i++) {
	    	probWordHashMap.put(masterWordList.get(i), sumFakeTrueOfWordHashMap.get(masterWordList.get(i)) / totWords);
	    }
	    
	    for(int i=0; i < masterWordList.size(); i++) {
	    	probWordFakeHashMap.put(masterWordList.get(i), fakeWordCountHashMap.get(masterWordList.get(i)) / fakeWordList.size());
	    }
	    
	    for(int i=0; i < masterWordList.size(); i++) {
	    	probWordTrueHashMap.put(masterWordList.get(i), trueWordCountHashMap.get(masterWordList.get(i)) / trueWordList.size());
	    }
	    
	    System.out.println();
	    System.out.println("probWordHashMap-----> " + probWordHashMap);
	    System.out.println("probWordFakeHashMap-> " + probWordFakeHashMap);
	    System.out.println("probWordTrueHashMap-> " + probWordTrueHashMap);
	    
	    // Need to implement final prob calculations
	    // Then need to set up to take as input a new doc to test
	    // to determine if it's true or fake

	}

}
