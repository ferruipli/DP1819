
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PhaseRepository;

@Service
@Transactional
public class PhaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PhaseRepository	phaseRepository;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	private PhaseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods--------------------------------------------------
}
