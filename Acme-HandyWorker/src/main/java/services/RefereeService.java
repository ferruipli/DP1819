
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.RefereeRepository;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods--------------------------------------------------
}
