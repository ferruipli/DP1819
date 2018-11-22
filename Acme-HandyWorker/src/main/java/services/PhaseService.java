
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PhaseRepository	phaseRepository;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// El n�mero de cada fase debe pasarse como par�metro desde el controlador,
	// para asegurar que los n�meros son consecutivos
	// COMPLT: dejar hasta aclarar atributo de orden
	public Phase create(final int number) {
		Phase result;

		result = new Phase();
		result.setNumber(number);

		return result;
	}

	public Phase save(final Phase phase) {
		Phase result;

		result = this.phaseRepository.save(phase);

		return result;
	}

	// Other business methods--------------------------------------------------
}
