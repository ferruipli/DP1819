
package services;

import org.springframework.beans.factory.annotation.Autowired;

import repositories.TutorialRepository;

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

	//Other business methods-------------------------------------------
}
