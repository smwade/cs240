package hangman;

import java.util.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EvilHangman implements IEvilHangmanGame {
	
	private int wordLength; // >= 2
	private Set<String> curSet;
	private Set<Character> guessedLetters;
	private Map<String, Set<String>> compMap;
	private String curWord;
	
	
	public EvilHangman() {
		this.wordLength = 0;
		this.curSet = new HashSet<String>();
		this.guessedLetters = new HashSet<Character>();
		this.compMap = new HashMap<String, Set<String>>();
	}
	
	public void setCurSet(Set<String> newCurSet) {
		this.curSet = newCurSet;
	}
	
	public Set<String> getCurSet() {
		return this.curSet;
	}
	
	public String getCurWord() {
		return curWord;
	}
	
	public Set<Character> getGuessedLetters() {
		return guessedLetters;
	}
	
	public Set<String> parseDictionary(File dictionary, int wordLength) throws FileNotFoundException{
		Set<String> createdSet= new HashSet<String>();
		Scanner scanner = new Scanner(dictionary);
		while (scanner.hasNext()) {
			String word = scanner.next().toLowerCase();
			if (word.length() == wordLength) {
				createdSet.add(word);
			}
		}
		scanner.close();
		if (createdSet.isEmpty()) {
			System.out.println("The dicitonary is empty...\n");
			System.exit(0);
		}
 		return createdSet;	
	}
	
	public void startGame(File dictionary, int wordLength) {
		try {
			this.wordLength = wordLength;
			curSet = parseDictionary(dictionary, wordLength);
			guessedLetters.clear();
			this.wordLength = wordLength;
			compMap.clear();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < wordLength; i++) {
				sb.append('-');
			}
			this.curWord = sb.toString();
		}
		catch (FileNotFoundException e) {
			System.out.printf("File not found...");
			System.exit(0);
		}
	}
	
	public String makePattern(String word, String curWord, char guess) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess) {
				sb.append(guess);
			}
			else {
				sb.append(curWord.charAt(i));
			}
		}
		return sb.toString();
	}
	
	//Helper function to makeGuess()
	public int charInString(String str, char ch) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				counter++;
			}
		}
		return counter;
	}
	
	//Helper function to makeGuess()
	public String whoHasRightmostLetter(String key1, String key2, char guess) {
		for (int i = key1.length() - 1; i >= 0; i--) {
			if (key1.charAt(i) != guess && key2.charAt(i) == guess)  {
				return key2;
			}
			else if (key1.charAt(i) == guess && key2.charAt(i) != guess)  {
				return key1;
			}
		}
		return "none";
	}
	
	
	
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		if (guessedLetters.contains(new Character(guess))) {
			throw new GuessAlreadyMadeException();
		}
		compMap.clear();
		for (String word: curSet) {
			String pattern = makePattern(word, curWord, guess);
			if (compMap.containsKey(pattern)) {
				compMap.get(pattern).add(word);
			} 
			else {
				Set<String> newSet = new HashSet<String>();
				newSet.add(word);
				compMap.put(pattern, newSet);
			}
		}
		int bestSize = 0;
		String bestKey = "";
		for (String key: compMap.keySet()) {
			int curSetSize = compMap.get(key).size();
			if (curSetSize > bestSize) {
				bestKey = key;
				bestSize = curSetSize;
			}
			else if (curSetSize == bestSize) {
				//choose group where letter dosnt appear at all
				//if (key.indexOf(guess) == -1 && bestKey.indexOf(guess) != -1) {
				//	bestKey = key;
				//	bestSize = curSetSize;
				//}
				//choose the group with fewer letters
				if (charInString(key, guess) < charInString(bestKey, guess)) {
					bestKey = key;
					bestSize = curSetSize;
				}
				//choose the group with the rightmost letter
				else if (charInString(key, guess) == charInString(bestKey, guess)) {
					String rightWinner = whoHasRightmostLetter(bestKey, key, guess);
					if (rightWinner.equals(key)) {
						bestKey = key;
						bestSize = curSetSize;
					}
				}
			}
		}
		guessedLetters.add(new Character(guess));
		curWord = bestKey;
		curSet = compMap.get(bestKey);
		return compMap.get(bestKey);
	}
	
}


