package classifier;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Classifier {
	
	public ArrayList<String> createMasterList(ArrayList<String> fakeWords, ArrayList<String> trueWords) {
		ArrayList<String> masterWordList = new ArrayList<String>();
		for(int i=0; i < fakeWords.size(); i++) {
	    	masterWordList.add(fakeWords.get(i));
	    }
		for(int i=0; i < trueWords.size(); i++) {
	    	masterWordList.add(trueWords.get(i));
	    }
		return masterWordList;
	}
	
	public ArrayList<String> removeDuplicates(ArrayList<String> list) {
		Set<String> listWithoutDuplicates = new LinkedHashSet<String>(list);
		list.clear();
		list.addAll(listWithoutDuplicates);
		return list;
	}
	
	public float wordCount(String word, ArrayList<String> wordList) {
		int count = 0;
		for(int i=0; i < wordList.size(); i++) {
			if(word.equals(wordList.get(i))) {
				count++;
			}
		}
		return count;
	}
}
