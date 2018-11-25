
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
	private TutorialService		tutorialService;
	@Autowired
	private HandyWorkerService	handyWorkerService;


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
		HandyWorker handyWorker;
		HandyWorker principal;

		if (section.getId() != 0) {
			handyWorker = this.sectionRepository.findHandyWorkerBySection(section.getId());
			principal = this.handyWorkerService.findByPrincipal();
			Assert.isTrue(handyWorker.equals(principal));
		}

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
		HandyWorker handyWorker;
		HandyWorker principal;

		Assert.isTrue(section.getId() != 0);
		Assert.notNull(section);
		Assert.isTrue(this.sectionRepository.exists(section.getId()));

		if (section.getId() != 0) {
			handyWorker = this.sectionRepository.findHandyWorkerBySection(section.getId());
			principal = this.handyWorkerService.findByPrincipal();
			Assert.isTrue(handyWorker.equals(principal));
		}

		tutorial = this.findTutorialBySection(section);
		this.removeSection(tutorial, section);

		this.sectionRepository.delete(section);

		Assert.isTrue(!(tutorial.getSections().contains(section)));
	}
	//Other business methods-------------------------------------------

	protected HandyWorker findHandyWorkerBySection(final int id) {
		HandyWorker handyWoker;
		handyWoker = this.sectionRepository.findHandyWorkerBySection(id);
		return handyWoker;
	}
	public void removeSection(final Tutorial tutorial, final Section section) {
		Assert.isTrue((tutorial.getSections().contains(section)));
		tutorial.getSections().remove(section);
		Assert.isTrue(!(tutorial.getSections().contains(section)));
	}
	//Este metodo se ha creado para cuando en una vista quiera añadirse secciones a una tutoria
	//si se hace a traves de una list que no haga falta meter uno a uno las section
	public void addSectionToTutorial(final Tutorial tutorial, final Collection<Section> sec) {
		Collection<Section> sections;
		sections = tutorial.getSections();
		sections.addAll(sec);
		tutorial.setSections(sections);
		Assert.isTrue(tutorial.getSections().contains(sec));
	}

	public Tutorial findTutorialBySection(final Section section) {
		Tutorial tutorial;
		tutorial = this.sectionRepository.findTutorialBySection(section.getId());
		Assert.isTrue((tutorial.getSections().contains(section)));
		return tutorial;
	}

}
