
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

	public Phase saveNewPhase(final int fixUpTaskId, final Phase phase) {
		Assert.isTrue(!this.phaseRepository.exists(phase.getId()));
		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));

		FixUpTask fixUpTask;
		Phase saved;
		HandyWorker principal;
		Collection<FixUpTask> workableFixUpTasks;

		principal = this.handyWorkerService.findByPrincipal();
		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		workableFixUpTasks = this.fixUpTaskService.findWorkableFixUpTasks(principal.getId());

		Assert.isTrue(workableFixUpTasks.contains(fixUpTask));

		// Phase dates must be between FixUpTask::startDate and FixUpTask::endDate
		Assert.isTrue(phase.getStartMoment().after(fixUpTask.getStartDate()) && phase.getStartMoment().before(fixUpTask.getEndDate()));
		Assert.isTrue(phase.getEndMoment().after(fixUpTask.getStartDate()) && phase.getEndMoment().before(fixUpTask.getEndDate()));

		saved = this.phaseRepository.save(phase);
		this.fixUpTaskService.addNewPhase(fixUpTask, saved);

		return saved;
	}

	public Phase update(final Phase phase) {
		Assert.isTrue(this.phaseRepository.exists(phase.getId()));
		Assert.isTrue(phase.getStartMoment().before(phase.getEndMoment()));
		this.checkCreator(phase);

		// Phase dates must be between FixUpTask::startDate and FixUpTask::endDate
		Assert.isTrue(this.checkPhaseDate(phase.getId()));

		Phase result;

		result = this.phaseRepository.save(phase);

		return result;
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);
		Assert.isTrue(this.phaseRepository.exists(phase.getId()));
		this.checkCreator(phase);

		FixUpTask fixUpTask;

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
		principalId = principal.getId();
		ownerId = this.handyWorkerService.findPhaseCreator(phase);

		Assert.isTrue(principalId == ownerId);
	}

	private boolean checkPhaseDate(final int phaseId) {
		boolean result;

		result = this.phaseRepository.checkPhaseDate(phaseId);

		return result;
	}
}
