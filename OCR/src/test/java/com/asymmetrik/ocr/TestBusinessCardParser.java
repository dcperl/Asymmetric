package com.asymmetrik.ocr;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit test to test the BusinessCardParser.
 * 
 * @author Whitney Simmons
 *
 */
public class TestBusinessCardParser {
	
	BusinessCardParser businessCardParser = new BusinessCardParser();

	@Test
	public void testExample1() {
		StringBuilder card1 = new StringBuilder();
		card1.append("ASYMMETRIK LTD").append("\n");
		card1.append("Mike Smith").append("\n");
		card1.append("Senior Software Engineer").append("\n");
		card1.append("(410)555-1234").append("\n");
		card1.append("msmith@asymmetrik.com");
		
		ContactInfo contact1 = businessCardParser.getContactInfo(card1.toString());
		assertEquals(contact1.getName(), "Mike Smith");
		assertEquals(contact1.getPhoneNumber(), "4105551234");
		assertEquals(contact1.getEmailAddress(), "msmith@asymmetrik.com");
	}
	
	@Test
	public void testExample2() {
		StringBuilder card2 = new StringBuilder();
		card2.append("Foobar Technologies").append("\n");
		card2.append("Analytic Developer").append("\n");
		card2.append("Lisa Haung").append("\n");
		card2.append("1234 Sentry Road").append("\n");
		card2.append("Columbia, MD 12345").append("\n");
		card2.append("Phone: 410-555-1234").append("\n");
		card2.append("Fax: 410-555-4321").append("\n");
		card2.append("lisa.haung@foobartech.com").append("\n");

		ContactInfo contact2 = businessCardParser.getContactInfo(card2.toString());
		assertEquals(contact2.getName(), "Lisa Haung");
		assertEquals(contact2.getPhoneNumber(), "4105551234");
		assertEquals(contact2.getEmailAddress(), "lisa.haung@foobartech.com");
	}
	
	@Test
	public void testExample3() {
		StringBuilder card3 = new StringBuilder();
		card3.append("Arthur Wilson").append("\n");
		card3.append("Software Engineer").append("\n");
		card3.append("Decision & Security Technologies").append("\n");
		card3.append("ABC Technologies").append("\n");
		card3.append("123 North 11th Street").append("\n");
		card3.append("Suite 229").append("\n");
		card3.append("Arlington, VA 22209").append("\n");
		card3.append("Tel: +1 (703) 555-1259").append("\n");
		card3.append("Fax: +1 (703) 555-1200").append("\n");
		card3.append("awilson@abctech.com").append("\n");
		
		ContactInfo contact3 = businessCardParser.getContactInfo(card3.toString());
		
		assertEquals(contact3.getName(), "Arthur Wilson");
		assertEquals(contact3.getPhoneNumber(),"17035551259");
		assertEquals(contact3.getEmailAddress(),"awilson@abctech.com");
	}

}
