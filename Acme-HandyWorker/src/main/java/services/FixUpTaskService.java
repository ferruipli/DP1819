
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Phase;

@Service
@Transactional
public class FixUpTaskService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private CustomerService		customerService;


	// Constructor ------------------------------------------------------------

	public FixUpTaskService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FixUpTask create() {
		FixUpTask result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new FixUpTask();
		result.setTicker(this.utilityService.generateValidTicker());
		result.setPublicationMoment(moment);
		result.setComplaints(Collections.<Complaint> emptySet());
		result.setApplications(Collections.<Application> emptySet());
		result.setPhases(Collections.<Phase> emptySet());
		result.setCustomer(this.customerService.findByPrincipal());

		return result;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		FixUpTask result;
		Customer principal;

		Assert.isTrue(fixUpTask.getWarranty().getFinalMode());
		Assert.isTrue(fixUpTask.getApplications().isEmpty()); // You cannot update a FixUpTaks with an Application associated
		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(fixUpTask.getCustomer()));

		result = this.fixUpTaskRepository.save(fixUpTask);

		if (this.fixUpTaskRepository.exists(fixUpTask.getId()))
			this.customerService.addFixUpTask(result.getCustomer(), result);

		return result;
	}

	public void delete(final FixUpTask fixUpTask) {
		Customer principal;

		Assert.isTrue(fixUpTask.getApplications().isEmpty()); // You cannot delete a FixUpTaks with an Application associated

		principal = this.customerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(fixUpTask.getCustomer()));

		// COMPLT: this.customerService.removeFixUpTask(principal, fixUpTask);

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	public FixUpTask findOne(final int fixUpTaskId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(fixUpTaskId);
		Assert.notNull(result);

		return result;
	}

	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	protected void addNewPhase(final FixUpTask fixUpTask, final Phase phase) {
		fixUpTask.getPhases().add(phase);
		this.fixUpTaskRepository.flush();
		this.fixUpTaskRepository.save(fixUpTask);
	}

	public Collection<FixUpTask> findWorkableFixUpTasks(final int handyWorkerId) {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findWorkableFixUpTasks(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	public FixUpTask findByPhase(final Phase phase) {
		FixUpTask result;

		Assert.isTrue(phase.getId() != 0);
		result = this.fixUpTaskRepository.findByPhase(phase);
		Assert.notNull(result);

		return result;
	}

	protected void addComplaint(final FixUpTask fixUpTask, final Complaint complaint) {
		fixUpTask.getComplaints().add(complaint);
	}

	protected void removePhase(final FixUpTask fixUpTask, final Phase phase) {
		fixUpTask.getPhases().remove(phase);
	}
}
