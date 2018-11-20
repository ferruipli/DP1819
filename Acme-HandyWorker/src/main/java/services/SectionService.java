
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
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

		result = this.sectionRepository.save(section);

		return result;
	}

	public Section findOne(final int idSection) {
		Section result;

		Assert.isTrue(idSection != 0);

		result = this.sectionRepository.findOne(idSection);

		return result;
	}
	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Section section) {
		Tutorial tutorial;
		Assert.isTrue(section.getId() != 0);
		Assert.notNull(section);
		Assert.isTrue(this.sectionRepository.exists(section.getId()));

		tutorial = this.tutorialService.findTutorialBySection(section);
		this.tutorialService.removeSection(tutorial, section);

		this.sectionRepository.delete(section);

		Assert.isTrue(!(tutorial.getSections().contains(section)));
	}
	//Other business methods-------------------------------------------
}
