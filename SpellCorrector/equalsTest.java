package spell;

import java.io.IOException;

import spell.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class equalsTest {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String dictionaryFileName2 = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector corrector1 = new SpellCorrector();
		SpellCorrector corrector2 = new SpellCorrector();
		
		corrector1.useDictionary(dictionaryFileName);
		corrector2.useDictionary(dictionaryFileName2);
		
		
		System.out.println(corrector1.trie.equals(corrector2.trie));
	}

}