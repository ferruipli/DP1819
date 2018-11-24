
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor ------------------------------------------------------------

	public ComplaintService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Complaint create() {
		Complaint result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Complaint();
		result.setTicker(this.utilityService.generateValidTicker());
		result.setMoment(moment);

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Complaint result;
		Customer principal;

		Assert.isTrue(!this.complaintRepository.exists(complaint.getId()));
		principal = this.customerService.findByPrincipal();
		Assert.isTrue(complaint.getFixUpTask().getCustomer().equals(principal));

		result = this.complaintRepository.save(complaint);

		this.fixUpTaskService.addComplaint(complaint.getFixUpTask(), result);

		return result;
	}

	public Complaint findOne(final int complaintId) {
		Complaint result;

		result = this.complaintRepository.findOne(complaintId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	// COMPLT: al acabar, comprobar que todos estos m�todos me sirven de verdad
	public Collection<Complaint> findByCustomerPrincipal() {
		Collection<Complaint> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.complaintRepository.findByCustomerPrincipal(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Complaint> findNotSelfAssigned() {
		Collection<Complaint> result;

		result = this.complaintRepository.findNotSelfAssigned();
		Assert.notNull(result);

		return result;
	}

	// Every change (such as .add(Object), .remove(Object), ...) in the collection 
	// returned by this method, is persisted in the database.
	public Collection<Complaint> findSelfAssignedByPrincipal() {
		Collection<Complaint> result;
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		result = principal.getComplaints();

		return result;
	}

	public Collection<Complaint> findInvolvedByHandyWorkerId(final int handyWorkerId) {
		Collection<Complaint> result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		result = this.complaintRepository.findInvolvedByHandyWorkerId(principal.getId());
		Assert.notNull(result);

		return result;
	}

	public Complaint findByReportId(final int reportId) {
		Complaint result;

		result = this.complaintRepository.findByReportId(reportId);
		Assert.notNull(result);

		return result;
	}

	protected void addReport(final Complaint complaint, final Report report) {
		complaint.setReport(report);
	}

}
