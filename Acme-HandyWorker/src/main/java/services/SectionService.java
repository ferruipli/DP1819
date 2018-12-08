
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	// Managed repository ---------------------------------------------
	@Autowired
	private SectionRepository	sectionRepository;

	// Supporting services -------------------------------------------	
	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private TutorialService		tutorialService;


	//Constructor ----------------------------------------------------
	public SectionService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Section create() {
		Section result;

		result = new Section();

		return result;
	}

	public Section save(final Section section) {
		Assert.notNull(section);

		Section result;

		if (section.getId() != 0)
			this.checkByPrincipal(section);

		result = this.sectionRepository.save(section);

		return result;
	}

	public Section findOne(final int sectionId) {
		Assert.isTrue(sectionId != 0);

		Section result;

		result = this.sectionRepository.findOne(sectionId);

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public void delete(final Section section) {
		Assert.notNull(section);
		Assert.isTrue(section.getId() != 0);
		this.checkByPrincipal(section);

		Tutorial tutorial;

		tutorial = this.tutorialService.findTutorialBySection(section);

		this.removeSectionToTutorial(tutorial, section);

		this.sectionRepository.delete(section);
	}

	//Other business methods-------------------------------------------
	protected void checkByPrincipal(final Section section) {
		HandyWorker handyWorker;
		HandyWorker principal;

		handyWorker = this.handyWorkerService.findHandyWorkerBySection(section.getId());
		principal = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(handyWorker.equals(principal));
	}

	public void removeSectionToTutorial(final Tutorial tutorial, final Section section) {
		tutorial.getSections().remove(section);
	}

	public void addSectionToTutorial(final Tutorial tutorial, final Section sec) {
		Collection<Section> sections;

		sections = tutorial.getSections();
		sections.add(sec);

		tutorial.setSections(sections);
	}

}
