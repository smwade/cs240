import hangman.*;
import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Main {
	
	String curString;
	public static void main(String[] args) {
		try {
			Main m = new Main();
			if (args.length != 3) {
				throw new Exception();
			}
			int wordLength = Integer.parseInt(args[1]);
			int numGuess = Integer.parseInt(args[2]);
			if (wordLength < 2) {
				System.out.println("word length is less than 2\n");
				throw new Exception();
			}
			if (numGuess < 1) {
				System.out.println("number of guesses is less than 2\n");
				throw new Exception();
			}
			File dictionary = new File(args[0]);
			m.runMain(wordLength, numGuess, dictionary);
		}
		catch (FileNotFoundException e) {
			System.out.printf("%s was not found...\n", args[0]);
		}
		catch (GuessAlreadyMadeException e) {
			System.out.printf("\nGuess already made...\n", args[0]);
		}
		catch (Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println(exceptionAsString);
			System.out.println("Incorrect: Ussage 'java Main [dicitonary] [wordLength] [guesses]");
		}
		
	}
	
	public void runMain(int wordLength, int numGuess, File dictionary) throws GuessAlreadyMadeException {
		
			//Begin Program
			boolean wrongInput = true;
			String charGuess = "";
			Set<String> returnedSet = new HashSet<String>();
			EvilHangman evilH = new EvilHangman();
			evilH.startGame(dictionary, wordLength);
			boolean correctGuess = false;
			while (numGuess > 0) {
				System.out.printf("\nYou have %s guesses left\n", numGuess);
				System.out.printf("Used letters:");
				for (char ch: evilH.getGuessedLetters()) {
					System.out.printf(" %s", ch);
				}
				System.out.printf("\nWord: %s", evilH.getCurWord());
				Scanner scanner = new Scanner(System.in);
				while (wrongInput) {
					System.out.printf("\nEnter guess: ");
					charGuess = scanner.next();
					if (charGuess.length() == 1) {
						try {
							char ch = charGuess.charAt(0);
							if (Character.isLetter(ch)) {
								wrongInput = false;
							}
						}
						catch (Exception e) {
							System.out.println("Incorrect input... try again");
						}
					}
					if (wrongInput) {
						System.out.println("Incorrect input... try again");
					} 
					else {
						try {
							charGuess.toLowerCase();
							returnedSet = evilH.makeGuess(charGuess.charAt(0));
						}
						catch (GuessAlreadyMadeException e) {
							System.out.println("Already printed this letter, try again");
							wrongInput = true;
						}
					}
				}
				curString = evilH.getCurWord();
				wrongInput = true;
				
				//System.out.println(returnedSet.size());
				if (curString.contains(charGuess)) {
					System.out.printf("Yes, %s is in the word\n", charGuess);
				} else {
					System.out.printf("Sorry, there are no %s's\n", charGuess);
				}
				if (returnedSet.size() == 1 && curString.indexOf(charGuess) != -1 && curString.indexOf('-') == -1) {
					System.out.printf("You win!\n");
					System.out.printf("The word was: %s\n", curString);
					return;
				} 
				evilH.setCurSet(returnedSet);
				numGuess--;
			}
			System.out.println("You lose!");
			System.out.printf("The word was: %s\n", evilH.getCurSet().iterator().next());
			
	}
}
