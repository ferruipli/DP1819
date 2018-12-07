
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;

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
		Collection<String> curriculumsTickers, fixUpTaskTickers, complaintTickers;

		currentDate = LocalDate.now();
		year = currentDate.getYear() % 100;
		month = currentDate.getMonthOfYear();
		day = currentDate.getDayOfMonth();
		curriculumsTickers = this.curriculumService.findAllTickers();
		fixUpTaskTickers = this.fixUpTaskService.findAllTickers();
		complaintTickers = this.complaintService.findAllTickers();

		numbers = String.format("%02d", year) + "" + String.format("%02d", month) + "" + String.format("%02d", day) + "-";
		counter = 0;

		do {
			result = numbers + this.createRandomLetters();
			counter++;
		} while (curriculumsTickers.contains(result) || fixUpTaskTickers.contains(result) || complaintTickers.contains(result) || counter < 650000);

		Assert.isTrue(counter == 650000); // Avoid infinite loops in the case of all possible tickers are already taken.

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

}
