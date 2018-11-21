
package services;

import java.util.Random;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;

@Service
@Transactional
public class UtilityService {

	// Managed repository ------------------------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services -----------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;


	// Constructors ------------------------------------------------------------

	public UtilityService() {
		super();
	}

	// Simple CRUD methods -----------------------------------------------------

	// Other business methods --------------------------------------------------

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
		} while (this.curriculumRepository.exists(this.curriculumService.findCurriculumByTicker(result).getId()) || counter < 650000);

		Assert.isTrue(counter == 650000);

		return result;
	}

	// Private methods ---------------------------------------------------------

	private String createRandomLetters() {
		String result, characters;
		Random randomNumber;

		result = "";
		randomNumber = new Random();
		characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		for (int i = 0; i <= 3; i++)
			result += characters.charAt(randomNumber.nextInt(characters.length()));

		return result;
	}

	/*
	 * private boolean existCurriculum(final String ticker) {
	 * Boolean result;
	 * 
	 * if (this.curriculumRepository.exists(this.curriculumService.findCurriculumByTicker(ticker).getId()))
	 * result = true;
	 * else
	 * result = false;
	 * 
	 * return result;
	 * 
	 * }
	 */

}
