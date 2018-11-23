
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PhaseRepository	phaseRepository;


	// Supporting services ----------------------------------------------------

	//	@Autowired
	//	private HandyWorkerService	handyWorkerService;

	// Constructor ------------------------------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Phase create(final int number) {
		Phase result;

		result = new Phase();

		return result;
	}

	public Phase save(final Phase phase) {
		Phase result;

		// This method is only available for update purposes.
		Assert.isTrue(this.phaseRepository.exists(phase.getId()));
		this.checkOwner(phase);

		result = this.phaseRepository.save(phase);

		return result;
	}

	public void delete(final Phase phase) {
		// COMPLT: comprobar que el handyWorker principal es el dueño de dicha fase
		// COMPLT: es necesario eliminar la fase previamente del fixUpTask??
		this.phaseRepository.delete(phase);
	}

	public Phase findOne(final int phaseId) {
		Phase result;

		result = this.phaseRepository.findOne(phaseId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	// COMPLT: mover a HandyWorkerService
	//	public int findPhaseCreator(final Phase phase) {
	//		int result;
	//
	//		result = this.phaseRepository.findPhaseCreatorId(phase);
	//
	//		return result;
	//	}

	public Collection<Phase> findByFixUpTaskIdOrdered(final int fixUpTaskId) {
		Collection<Phase> result;

		result = this.phaseRepository.findByFixUpTaskIdOrdered(fixUpTaskId);
		Assert.notNull(result);

		return result;
	}

	private void checkOwner(final Phase phase) {
		// COMPLT: 
		//		int principalId, ownerId;
		//
		//		principalId = this.handyWorkerService.findByPrincipal().getId();
		//		ownerId = this.handyWorkerService.findPhaseCreator(phase);
		//
		//		Assert.isTrue(principalId == ownerId);
	}
}
