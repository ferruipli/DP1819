
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorsableRepository;

@Service
@Transactional
public class EndorsableService {

	// Managed repository ------------------------------
	@Autowired
	private EndorsableRepository	endorsableRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public EndorsableService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	// Other business methods --------------------------

}
