
package services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UtilityService {

	// Managed repository ------------------------------------------------------

	// Supporting services -----------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


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
		final Set<String> curriculumTickers;

		currentDate = LocalDate.now();
		year = currentDate.getYear() % 100;
		month = currentDate.getMonthOfYear();
		day = currentDate.getDayOfMonth();

		numbers = String.format("%02d", year) + "" + String.format("%02d", month) + "" + String.format("%02d", day) + "-";
		//curriculumTickers = new HashSet<>(this.curriculumService.findAllTickers());
		curriculumTickers = new HashSet<>();
		counter = 0;

		do {
			result = numbers + this.createRandomLetters();
			counter++;
		} while (curriculumTickers.contains(result) || counter < 650000);

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

}
