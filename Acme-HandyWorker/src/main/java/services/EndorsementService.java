
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorsementRepository;

@Service
@Transactional
public class EndorsementService {

	// Managed repository ------------------------------
	@Autowired
	private EndorsementRepository	endorsementRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public EndorsementService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	// Other business methods --------------------------

}
