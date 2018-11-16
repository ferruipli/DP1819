
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CustomisationRepository;

@Service
@Transactional
public class CustomisationService {

	// Managed repository ------------------------------
	@Autowired
	private CustomisationRepository	customisationRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public CustomisationService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	// Other business methods --------------------------

}
