package validation.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class it loads the Sentence validation rules and Validate the given sentence
 * 
 * @author Srinivas Ashadapu
 *
 */
public class SentenceValidator {

	// Map hold validation rules and  description
	private static final Map<String, Predicate<String>> validationRulesMap = new HashMap<String, Predicate<String>>();
	private static final Logger LOG = LogManager.getLogger(SentenceValidator.class);
	static {
		
		LOG.debug("*****Loading Sentence Validation rules*****" );
		// Rule 1: predicate to verify String starts with a Capital letter
		Predicate<String> startWithUpperCase = s -> Character.isUpperCase(s.charAt(0));
		validationRulesMap.put("Start with UpperCase Letter", startWithUpperCase);
		
		// Rule 2:Predicate to verify String ending with one of characters: ".", "?", "!"
		Predicate<String> endWithSplChar =  s -> {
			char lastChar = s.charAt(s.length() - 1);
			if (lastChar == '.' || lastChar == '?' || lastChar == '!') {
				return true;
			} else {
				return false;
			}
		};
		validationRulesMap.put("String ending with one of characters: \".\", \"?\", \"!\"", endWithSplChar);
	
		// Rule3: Predicate to verify String has no period character excluding last character
		// Rule 4: verify String has an even number of quotation marks.
		
		Predicate<String> evenQuotesAndPeriodtest =  s -> {
			boolean eventquote = true;
			boolean periodFound = false;
			int strlength = s.length();
			for (char c : s.toCharArray()) {
				strlength--;
				// Period found should not be last character , Once period found sentence is
				// invalid
				if (!periodFound && c == '.' && strlength != 0) {
					periodFound = true;
				}
				// even number of quotes
				if (c == '"') {
					eventquote = !eventquote;
				}
			}
			if (eventquote && !periodFound)
				return true;
			else
				return false;
		};
		validationRulesMap.put("String should contain Even quotation marks and no period char other than last character", evenQuotesAndPeriodtest);

		// Rule 5: Predicate to verify whether string contains number greater than 13
		Predicate<String> numberGreaterThanEql13 =  s -> {

			Pattern p = Pattern.compile("-?[0-9]+");
			Matcher m = p.matcher(s);
			while (m.find()) {
				if (Integer.parseInt(m.group()) >= 13)
				return false;
			}

			return true;
		};
		validationRulesMap.put("String should not contain Number greater than equal 13", numberGreaterThanEql13);
		
		LOG.debug("******Sentence Validation rules configured -> {}" ,validationRulesMap.keySet());

	}

	/**
	 * 
	 * @param inpSentence  
	 * 		Sentence to be validated
	 * @return
	 * 	true : if sentence validate against Rules configured
	 *  false : sentence failed to validate any one of the rule configured
	 */
	public static boolean isValidSentence(String inpSentence) {
		
		LOG.info("***** Enter -> {} method, Source Sentence -> {}**** ", "isValidSentence" ,inpSentence);
		boolean isSentenceValid = false;
		
		Objects.requireNonNull(inpSentence);

		if (inpSentence.isEmpty()) {
			LOG.info(" Source Sentence is Empty ");
			return isSentenceValid;
		}

		isSentenceValid =  validationRulesMap.entrySet().stream().allMatch(e -> {
			if (!e.getValue().test(inpSentence)) {
				LOG.debug("Validation Rule Failed -> {} For given Sentence ->{} ",e.getKey(), inpSentence  );
				return false;
			} else {
				LOG.debug(" Validation Rule Success -> {} For given Sentence ->{} "+ e.getKey(),inpSentence  );
				return true;
				
			}

		});
		
		LOG.info(" Sentence Valid ? ->{} -> {} ", inpSentence,isSentenceValid);
		LOG.info("**** Exit -> {}  method ******", "isValidSentence");
		return isSentenceValid;
		
		
	}

}
