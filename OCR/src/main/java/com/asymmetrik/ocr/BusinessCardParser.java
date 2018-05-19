package com.asymmetrik.ocr;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BusinessCardParser A parser given a string, will parse contact information
 * from a business card.
 * 
 * @author Whitney Simmons
 *
 */
public class BusinessCardParser {

	/**
	 * Pattern for valid e-mail addresses.
	 */
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PHONE_PATTERN = "(.*)\\d{3}(-|\\))(\\s)?\\d{3}-\\d{4}";
	private static final String NAME_PATTERN = "^[a-zA-Z]*\\s[a-zA-Z]*$";
	private static final String COMPANY_PATTERNS = "(.*)(LTD|Technologies|Inc)(.*)";
	private static final String TITLE_PATTERNS = "(.*)(Engineer|Developer|Manager)(.*)";

	private Pattern eMailPattern;
	private Pattern phonePattern;
	private Pattern namePattern;
	private Pattern companyPattern;
	private Pattern titlePattern;

	/**
	 * Constructor
	 */
	public BusinessCardParser() {
		eMailPattern = Pattern.compile(EMAIL_PATTERN);
		phonePattern = Pattern.compile(PHONE_PATTERN);
		namePattern = Pattern.compile(NAME_PATTERN);
		companyPattern = Pattern.compile(COMPANY_PATTERNS);
		titlePattern = Pattern.compile(TITLE_PATTERNS);
	}

	/**
	 * Given
	 * 
	 * @param document
	 *            this method will parse the string and then
	 * @return {@link ContactInfo}
	 */
	public ContactInfo getContactInfo(final String document) {
		ContactInfo newContact = new ContactInfo();

		String[] lines = document.split("\n");
		String itemFound;
		boolean phoneFound = false;
		for (String line : lines) {
			// Email
			itemFound = findEmail(line);
			if (itemFound != null) {
				newContact.setEmailAddress(itemFound);
				continue;
			}

			// Phone Number
			itemFound = findPhoneNumber(line);
			if (itemFound != null && !phoneFound) {
				newContact.setPhoneNumber(itemFound);
				// Being greedy; save the first number found.
				phoneFound = true;
				continue;
			}

			// Name
			itemFound = findName(line);
			if (itemFound != null) {
				newContact.setName(itemFound);
			}
		}

		return newContact;
	}

	/**
	 * Given
	 * @param line
	 * @return email found from the pattern match, otherwise return null.
	 */
	private String findEmail(final String line) {
		// Match e-mail
		Matcher matcher = eMailPattern.matcher(line);
		if (matcher != null && matcher.matches()) {
			return matcher.group(0);
		}
		return null;
	}

	/**
	 * Given
	 * @param line
	 * Applies a phone number pattern. If match format the line and return numbers.
	 * Otherwise return null.
	 * @return
	 */
	private String findPhoneNumber(final String line) {
		// Match phone number
		Matcher matcher = phonePattern.matcher(line);
		if (matcher != null && matcher.matches()) {
			String formattedNumber = matcher.group(0);
			formattedNumber = formattedNumber.replaceAll("\\s", "");
			formattedNumber = formattedNumber.replaceAll("\\D", "");
			return formattedNumber;
		}
		return null;
	}

	/**
	 * Given
	 * @param line
	 * Applies a name match and then rules out company and title patterns.
	 * @return matching name otherwise null
	 */
	private String findName(String line) {
		Matcher matcher = namePattern.matcher(line);
		if (matcher != null && matcher.matches()) {
			String nameMatch = matcher.group(0);
			// Check for company patterns
			Matcher companyMatch = companyPattern.matcher(nameMatch);
			if(companyMatch != null && companyMatch.matches()) {
				return null;
			}			
			Matcher titleMatch = titlePattern.matcher(nameMatch);
			if(titleMatch != null && titleMatch.matches()) {
				return null;
			}
			return nameMatch;
		}
		return null;
	}

}
