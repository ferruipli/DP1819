
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PhaseRepository		phaseRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructor ------------------------------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Phase create() {
		Phase result;

		result = new Phase();

		return result;
	}

	public void saveNewPhase(final int fixUpTaskId, final Phase phase) {
		FixUpTask fixUpTask;
		HandyWorker principal;
		Collection<FixUpTask> workableFixUpTasks;

		Assert.isTrue(!this.phaseRepository.exists(phase.getId()));
		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));
		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		workableFixUpTasks = this.fixUpTaskService.findWorkableFixUpTasks(principal.getId());
		Assert.isTrue(workableFixUpTasks.contains(fixUpTask));

		// Phase dates must be between FixUpTask::startDate and FixUpTask::endDate
		Assert.isTrue(phase.getStartMoment().after(fixUpTask.getStartDate()) && phase.getStartMoment().before(fixUpTask.getEndDate()));
		Assert.isTrue(phase.getEndMoment().after(fixUpTask.getStartDate()) && phase.getEndMoment().before(fixUpTask.getEndDate()));

		this.fixUpTaskService.addNewPhase(fixUpTask, phase);
	}

	public Phase update(final Phase phase) {
		Phase result;

		Assert.isTrue(this.phaseRepository.exists(phase.getId()));
		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));

		// Phase dates must be between FixUpTask::startDate and FixUpTask::endDate
		Assert.isTrue(this.phaseRepository.checkPhaseDate(phase.getId()));

		this.checkCreator(phase);

		result = this.phaseRepository.save(phase);

		return result;
	}

	public void delete(final Phase phase) {
		FixUpTask fixUpTask;

		this.checkCreator(phase);
		fixUpTask = this.fixUpTaskService.findByPhase(phase);
		this.fixUpTaskService.removePhase(fixUpTask, phase);

		this.phaseRepository.delete(phase);
	}

	public Phase findOne(final int phaseId) {
		Phase result;

		result = this.phaseRepository.findOne(phaseId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<Phase> findByFixUpTaskIdOrdered(final int fixUpTaskId) {
		Collection<Phase> result;

		result = this.phaseRepository.findByFixUpTaskIdOrdered(fixUpTaskId);
		Assert.notNull(result);

		return result;
	}

	private void checkCreator(final Phase phase) {
		HandyWorker principal;
		int principalId, ownerId;

		principal = this.handyWorkerService.findByPrincipal();
		Assert.notNull(principal);
		principalId = principal.getId();
		ownerId = this.handyWorkerService.findPhaseCreator(phase);

		Assert.isTrue(principalId == ownerId);
	}
}
