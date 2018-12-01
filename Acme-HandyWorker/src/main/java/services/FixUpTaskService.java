
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

		result = new FixUpTask();
		result.setTicker(this.utilityService.generateValidTicker());
		result.setComplaints(Collections.<Complaint> emptySet());
		result.setApplications(Collections.<Application> emptySet());
		result.setPhases(Collections.<Phase> emptySet());
		result.setCustomer(this.customerService.findByPrincipal());

		return result;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getWarranty().getFinalMode());
		Assert.isTrue(fixUpTask.getApplications().isEmpty()); // You cannot update a FixUpTaks with an Application associated
		this.checkByPrincipal(fixUpTask);
		this.utilityService.checkDate(fixUpTask.getStartDate(), fixUpTask.getEndDate());

		FixUpTask result;
		Date moment;

		moment = this.utilityService.current_moment();
		fixUpTask.setPublicationMoment(moment);

		result = this.fixUpTaskRepository.save(fixUpTask);

		if (!this.fixUpTaskRepository.exists(fixUpTask.getId()))
			this.customerService.addFixUpTask(result.getCustomer(), result);

		return result;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(this.fixUpTaskRepository.exists(fixUpTask.getId()));
		Assert.isTrue(fixUpTask.getApplications().isEmpty()); // You cannot delete a FixUpTaks with an Application associated
		this.checkByPrincipal(fixUpTask);

		this.customerService.removeFixUpTask(fixUpTask.getCustomer(), fixUpTask);

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

	public void checkByPrincipal(final FixUpTask fixUpTask) {
		Customer principal;

		principal = this.customerService.findByPrincipal();

		Assert.isTrue(principal.equals(fixUpTask.getCustomer()));
	}

	public double[] findDataNumberFixUpTaskPerUser() {
		double[] result;

		result = this.fixUpTaskRepository.findDataNumberFixUpTaskPerUser();

		return result;
	}

	public double[] findDataMaximumPrice() {
		double[] result;

		result = this.fixUpTaskRepository.findDataMaximumPrice();

		return result;
	}

	public double findRatioFixUpTaskWithComplaint() {
		double result;

		result = this.fixUpTaskRepository.findRatioFixUpTaskWithComplaint();

		return result;
	}

	public Collection<FixUpTask> findWorkableFixUpTasks(final int handyWorkerId) {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findWorkableFixUpTasks(handyWorkerId);
		Assert.notNull(result);

		return result;
	}

	protected Page<FixUpTask> findFixUpTaskFinder(final String keyWord, final Double startPrice, final Double endPrice, final Date startDate, final Date endDate, final String warranty, final String category, final Pageable pageable) {
		Page<FixUpTask> result;

		result = this.fixUpTaskRepository.findFixUpTaskFinder(keyWord, startPrice, endPrice, startDate, endDate, warranty, category, pageable);
		Assert.notNull(result);

		return result;
	}

	protected FixUpTask findByPhaseId(final int phaseId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findByPhaseId(phaseId);

		return result;
	}

	protected void addNewPhase(final FixUpTask fixUpTask, final Phase phase) {
		fixUpTask.getPhases().add(phase);
	}

	protected void addComplaint(final FixUpTask fixUpTask, final Complaint complaint) {
		fixUpTask.getComplaints().add(complaint);
	}

	protected void removePhase(final FixUpTask fixUpTask, final Phase phase) {
		fixUpTask.getPhases().remove(phase);
	}

	protected void addApplication(final FixUpTask fixUpTask, final Application application) {
		fixUpTask.getApplications().add(application);
	}
}
