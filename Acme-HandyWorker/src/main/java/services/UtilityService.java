
package services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.CreditCard;

@Service
@Transactional
public class UtilityService {

	// Managed repository ------------------------------------------------------

	// Supporting services -----------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ComplaintService	complaintService;


	// Constructors ------------------------------------------------------------

	public UtilityService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	// Other business methods --------------------------------------------------
	public void checkEmailActors(final Actor actor) {
		if (actor instanceof Administrator)
			Assert.isTrue(actor.getEmail().matches("[A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]|[A-Za-z_.]+[\\w]+@|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@+[\\>]"));
		else
			Assert.isTrue(actor.getEmail().matches("[A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"));
	}

	public void checkEmailRecords(final String email) {
		Assert.isTrue(email.matches("[A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+|[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"));
	}

	public String generateValidTicker() {
		String numbers, result;
		Integer day, month, year;
		LocalDate currentDate;
		Integer counter;

		currentDate = LocalDate.now();
		year = currentDate.getYear() % 100;
		month = currentDate.getMonthOfYear();
		day = currentDate.getDayOfMonth();

		numbers = String.format("%02d", year) + "" + String.format("%02d", month) + "" + String.format("%02d", day) + "-";
		counter = 0;

		do {
			result = numbers + this.createRandomLetters();
			counter++;
		} while (!(this.curriculumService.existTicker(result) == null) && !(this.fixUpTaskService.existTicker(result) == null) && !(this.complaintService.existTicker(result) == null) && counter < 650000);

		return result;
	}

	public void checkDate(final Date start, final Date end) {
		Assert.isTrue(start.before(end));
	}

	public Date current_moment() {
		Date result;

		result = new Date(System.currentTimeMillis() - 1);

		return result;
	}

	public void checkUsername(final Actor actor) {
		Assert.isTrue(!actor.getUserAccount().getUsername().equals("System"));
	}

	public void checkAttachments(final String attachments) {
		List<String> attachmentList;

		Assert.notNull(attachments);
		attachmentList = this.getSplittedAttachments(attachments);

		for (final String at : attachmentList)
			try {
				new URL(at);
			} catch (final MalformedURLException e) {
				throw new IllegalArgumentException("Invalid URL");
			}
	}

	public List<String> getSplittedAttachments(final String attachments) {
		List<String> result;
		String[] attachmentsArray;

		result = new ArrayList<>();
		attachmentsArray = attachments.split("\r");

		for (String at : attachmentsArray) {
			at = at.trim();
			if (!at.isEmpty())
				result.add(at);
		}

		return result;
	}

	// Private methods ---------------------------------------------------------

	private String createRandomLetters() {
		String result, characters;
		Random randomNumber;

		result = "";
		randomNumber = new Random();
		characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i <= 5; i++)
			result += characters.charAt(randomNumber.nextInt(characters.length()));

		return result;
	}

	//------------------CREDIT CARD------------------------------------
	public CreditCard createnewCreditCard() {
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setBrandName("XXX");
		creditCard.setCvvCode(123);
		creditCard.setExpirationMonth("01");
		creditCard.setExpirationYear("00");
		creditCard.setHolderName("XXX");
		creditCard.setNumber("XXXXXXXXXXXXXXX");

		return creditCard;
	}
	public boolean checkCreditCard(final CreditCard creditCard) {
		boolean res;
		res = true;

		String brandName;
		Integer cvvCode;
		String expirationMonth;
		String expirationYear;
		String holderName;
		String number;

		brandName = creditCard.getBrandName();
		cvvCode = creditCard.getCvvCode();
		expirationMonth = creditCard.getExpirationMonth();
		expirationYear = creditCard.getExpirationYear();
		holderName = creditCard.getHolderName();
		number = creditCard.getNumber();

		if (brandName.equals("") || expirationMonth.equals("") || expirationYear.equals("") || holderName.equals("") || cvvCode > 999 || cvvCode < 100 || number.length() > 18 || number.length() < 13)
			res = false;
		return res;

	}

	public Boolean checkIfCreditCardChanged(final CreditCard creditCard) {
		String brandName;
		String expirationMonth;
		String expirationYear;
		String holderName;
		String number;
		Integer cvvCode;
		Boolean result;

		result = false;
		brandName = creditCard.getBrandName();
		expirationMonth = creditCard.getExpirationMonth();
		expirationYear = creditCard.getExpirationYear();
		holderName = creditCard.getHolderName();
		number = creditCard.getNumber();
		cvvCode = creditCard.getCvvCode();

		//If creditCard is changed
		if (!(brandName.equals("XXX") && expirationMonth.equals("XXX") && expirationYear.equals("XXX") && holderName.equals("XXX") && number.equals("XXXXXXXXXXXXXXX") && cvvCode.equals(123)))
			Assert.isTrue(this.checkCreditCard(creditCard));

		result = true;
		return result;

	}

}
