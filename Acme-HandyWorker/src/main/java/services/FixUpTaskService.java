
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
		// COMPLT: fixUpTask.setCustomer(Customer.findByPrincipal);

		return result;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		FixUpTask result;

		result = this.fixUpTaskRepository.save(fixUpTask);

		// COMPLT: 
		//if(fixUpTask.getId() == 0) {
		//	añadir addFixUpTask al customer principal
		//}

		return result;
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

	protected void addComplaint(final FixUpTask fixUpTask, final Complaint complaint) {
		fixUpTask.getComplaints().add(complaint);
	}
}
