
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
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

		result = new Tutorial();

		return result;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Tutorial result;

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
}
