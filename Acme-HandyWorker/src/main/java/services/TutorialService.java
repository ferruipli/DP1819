
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.HandyWorker;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository ---------------------------------------------
	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor ----------------------------------------------------
	public TutorialService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Tutorial create() {
		Tutorial result;
		Date date;
		final HandyWorker principal;
		Collection<Sponsorship> sponsorships;
		//TODO da fallo con el getPrincipal
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);

		result = new Tutorial();
		date = new Date(System.currentTimeMillis() - 1);
		sponsorships = new ArrayList<Sponsorship>();

		result.setMoment(date);
		result.setHandyWorker(principal);
		result.setSponsorShips(sponsorships);

		return result;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Tutorial result;

		if (tutorial.getId() != 0) {
			Date dateNow;
			dateNow = new Date();
			Assert.isTrue(tutorial.getMoment().before(dateNow));
		}

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
		HandyWorker handyWorker;
		HandyWorker principal;
		Assert.isTrue(tutorial.getId() != 0);
		Assert.notNull(tutorial);
		Assert.isTrue(this.tutorialRepository.exists(tutorial.getId()));

		handyWorker = tutorial.getHandyWorker();
		principal = this.handyWorkerService.findByPrincipal();
		Assert.isTrue(handyWorker.equals(principal));

		this.tutorialRepository.delete(tutorial);
	}
	//Other business methods-------------------------------------------
	protected Tutorial findTutorialBySponsorship(final Sponsorship sponsorship) {
		Tutorial tutorial;
		tutorial = this.tutorialRepository.findTutorialBySponsorship(sponsorship.getId());
		return tutorial;
	}

}
