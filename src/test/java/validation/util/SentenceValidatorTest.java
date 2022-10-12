package validation.util;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class SentenceValidatorTest {
	
	
	@Test
	public void testisValidSentenceWithNull() {
		 assertThrows(NullPointerException.class,
				 ()->SentenceValidator.isValidSentence(null));
		
	}
	@Test
	public void testisValidSentenceWithEmpty() {
		assertFalse(SentenceValidator.isValidSentence(""));
		
	}
	@Test
	public void testisValidSentenceWithInvalidStr() {
		assertFalse(SentenceValidator.isValidSentence("The quick brown fox said \"hello Mr. lazy dog\"."));
		assertFalse(SentenceValidator.isValidSentence("the quick brown fox said “hello Mr lazy dog\"."));
		assertFalse(SentenceValidator.isValidSentence("\"The quick brown fox said “hello Mr lazy dog.\""));
		assertFalse(SentenceValidator.isValidSentence("One lazy dog is too few, 13 is too many."));
		assertFalse(SentenceValidator.isValidSentence("Are there 11, 12, or 13 lazy dogs?"));
		assertFalse(SentenceValidator.isValidSentence("There is no punctuation in this sentence"));
	}
	@Test
	public void testisValidSentenceWithValidStr() {
		assertTrue(SentenceValidator.isValidSentence("The quick brown fox said \"hello Mr lazy dog\"."));
		assertTrue(SentenceValidator.isValidSentence("The quick brown fox said hello Mr lazy dog."));
		assertTrue(SentenceValidator.isValidSentence("One lazy dog is too few, 12 is too many."));
		assertTrue(SentenceValidator.isValidSentence("One lazy dog is too few, thirteen is too many."));
		assertTrue(SentenceValidator.isValidSentence("How many \"lazy dogs\" are there?"));
	}
	
	

}
