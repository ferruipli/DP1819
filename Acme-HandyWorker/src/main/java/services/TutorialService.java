
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.HandyWorker;
import domain.Section;
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

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private SectionService		sectionService;


	//Constructor ----------------------------------------------------
	public TutorialService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Tutorial create() {
		Tutorial result;
		final HandyWorker principal;
		Section section;
		Date moment;

		principal = this.handyWorkerService.findByPrincipal();
		section = new Section();
		moment = this.utilityService.current_moment();

		result = new Tutorial();
		result.setHandyWorker(principal);
		result.setSponsorShips(new ArrayList<Sponsorship>());
		result.setSections(new ArrayList<Section>());
		result.setMoment(moment);
		this.sectionService.addSectionToTutorial(result, section);

		return result;
	}
	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		this.checkByPrincipal(tutorial);

		Tutorial result;
		Date moment;

		moment = this.utilityService.current_moment();
		tutorial.setMoment(moment);

		result = this.tutorialRepository.save(tutorial);

		return result;
	}

	public Tutorial findOne(final int idTutorial) {
		Assert.isTrue(idTutorial != 0);

		Tutorial result;

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
		Assert.notNull(tutorial);
		Assert.isTrue(tutorial.getId() != 0);
		this.checkByPrincipal(tutorial);

		this.tutorialRepository.delete(tutorial);
	}

	protected void checkByPrincipal(final Tutorial tutorial) {
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(principal.equals(tutorial.getHandyWorker()));
	}

	//Other business methods-------------------------------------------
	public Tutorial findTutorialBySection(final Section section) {
		Tutorial tutorial;

		tutorial = this.tutorialRepository.findTutorialBySection(section.getId());

		return tutorial;
	}

	protected Tutorial findTutorialBySponsorship(final Sponsorship sponsorship) {
		Tutorial result;

		result = this.tutorialRepository.findTutorialBySponsorship(sponsorship.getId());

		return result;
	}
	public Page<Tutorial> findTutorialByHandyWorker(final HandyWorker handyWorker, final Pageable pageable) {
		Page<Tutorial> tutorials;

		tutorials = this.tutorialRepository.findTutorialByHandyWorker(handyWorker.getId(), pageable);

		return tutorials;
	}
	public Page<Tutorial> findAllTutorialPageable(final Pageable pageable) {
		Page<Tutorial> tutorials;

		tutorials = this.tutorialRepository.findAllTutorialPageable(pageable);

		return tutorials;
	}

}
