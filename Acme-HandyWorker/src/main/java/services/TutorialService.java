
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

public class TutorialService {

	// Managed repository ---------------------------------------------
	@Autowired
	private TutorialRepository	tutorialRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private TutorialService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Tutorial create() {
		Tutorial result;
		Date date;
		HandyWorker handyWorker;
		Collection<Sponsorship> sponsorships;

		result = new Tutorial();
		date = new Date(System.currentTimeMillis() - 1);
		handyWorker = new HandyWorker();
		sponsorships = new ArrayList<Sponsorship>();

		result.setMoment(date);
		result.setHandyWorker(handyWorker);
		result.setSponsorShips(sponsorships);

		return result;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Date dateNow;
		Tutorial result;

		dateNow = new Date();
		Assert.isTrue(tutorial.getMoment().before(dateNow));

		result = this.tutorialRepository.save(tutorial);

		return result;
	}
	public Tutorial findOne(final int idTutorial) {
		Tutorial result;

		Assert.isTrue(idTutorial != 0);

		result = this.tutorialRepository.findOne(idTutorial);

		return result;
	}
	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public void delete(final Tutorial tutorial) {
		Assert.isTrue(tutorial.getId() != 0);
		Assert.notNull(tutorial);
		Assert.isTrue(this.tutorialRepository.exists(tutorial.getId()));

		this.tutorialRepository.delete(tutorial);
	}
	//Other business methods-------------------------------------------
	public Tutorial findTutorialBySection(final Section section) {
		Tutorial tutorial;
		tutorial = this.tutorialRepository.findTutorialBySection(section.getId());
		Assert.isTrue((tutorial.getSections().contains(section)));
		return tutorial;
	}
	public void removeSection(final Tutorial tutorial, final Section section) {
		Assert.isTrue((tutorial.getSections().contains(section)));
		tutorial.getSections().remove(section);
		Assert.isTrue(!(tutorial.getSections().contains(section)));
	}
}
