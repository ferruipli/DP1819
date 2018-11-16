
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {

	// Managed repository ----------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting repositories -----------------------------

	// Constructors ----------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ---------------------------------

	// Other business methods ------------------------------

}
