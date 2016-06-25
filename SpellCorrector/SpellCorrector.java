package spell;

import java.io.*;
import java.util.*;


public class SpellCorrector implements ISpellCorrector{
	public Trie trie;
	
	public SpellCorrector() {
		trie = new Trie();
	}
	
	public SpellCorrector(String dictionaryFileName) throws IOException{
		trie = new Trie();
		useDictionary(dictionaryFileName);
	}
	
	@Override
	public String toString() {
		return trie.toString();
	}
	
	public void useDictionary(String dictionaryFileName) throws IOException {
		try {
			if (trie.nodeCount != 1) {
				trie = new Trie();
			}
			Scanner scan = new Scanner(new File(dictionaryFileName));
			while (scan.hasNext()) {
				trie.add(scan.next().toLowerCase());
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(dictionaryFileName + " could not be found.\n");
		}		
	}
		
	
	public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
		if (trie.find(inputWord.toLowerCase()) != null) {
			return inputWord;
		}
		Set<String> D1set = genrateDistanceSet(inputWord);
		List<String> winningWords = new ArrayList<String>();
		int max = 0;
		for (String str : D1set) {
			ITrie.INode strNode = trie.find(str);
			if (strNode != null) {
				if (strNode.getValue() > max) {
					winningWords.clear();
					winningWords.add(str);
					max = strNode.getValue();
				}
				else if (strNode.getValue() == max) {
					winningWords.add(str);
				}
			}
		}
		
		//now check words in D1
		if (!winningWords.isEmpty()) {
			Collections.sort(winningWords);
			return winningWords.get(0);
		}
		
		// now to D2
		winningWords.clear();
		for (String str : D1set) {
			Set<String> D2set = genrateDistanceSet(str);
			max = 0;
			for (String s : D2set) {
				ITrie.INode strNode = trie.find(s);
				if (strNode != null) {
					if (strNode.getValue() > max) {
						winningWords.clear();
						winningWords.add(s);
						max = strNode.getValue();
					}
					else if (strNode.getValue() == max) {
						winningWords.add(s);
					}
				}
			}
		}
		//now check words in D2
		if (!winningWords.isEmpty()) {
			Collections.sort(winningWords);
			return winningWords.get(0);
		}
		throw new NoSimilarWordFoundException();
	}
	
	public Set<String> genrateDistanceSet(String inputWord) {
		Set<String> set = new HashSet<String>();
		//Deletion
		for (int i = 0; i < inputWord.length(); i++) {
			StringBuilder word = new StringBuilder(inputWord);
			set.add(word.deleteCharAt(i).toString());
		}
		
		//Transpose
		for (int i = 0; i < inputWord.length() - 1; i++) {
			StringBuilder word = new StringBuilder(inputWord);
			char char1 = word.charAt(i);
			char char2 = word.charAt(i + 1);
			word.setCharAt(i, char2);
			word.setCharAt(i+1, char1);
			set.add(word.toString());
		}
		
		//Alteration
		for (int i = 0; i < inputWord.length(); i++) {
			for (int j = 0; j < 26; j++) {
				StringBuilder word = new StringBuilder(inputWord);
				int intChar = j + 'a';
				char alterChar = (char) intChar;
				word.setCharAt(i, alterChar);
				set.add(word.toString());
			}
		}
		
		//Insertion
		for (int i = 0; i < inputWord.length() + 1; i++) {
			for (int j = 0; j < 26; j++) {
				StringBuilder word = new StringBuilder(inputWord);
				int intChar = j + 'a';
				char alterChar = (char) intChar;
				word.insert(i, alterChar);
				set.add(word.toString());
			}
		}
		return set;
	}
}
