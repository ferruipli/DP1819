
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

		result = new Complaint();
		result.setTicker(this.utilityService.generateValidTicker());

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(!this.complaintRepository.exists(complaint.getId())); // Complaints cannot be updated

		Complaint result;
		Customer principal;
		Date moment;

		moment = this.utilityService.current_moment();
		principal = this.customerService.findByPrincipal();

		Assert.isTrue(complaint.getFixUpTask().getCustomer().equals(principal));

		complaint.setMoment(moment);
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

	public Page<Complaint> findByCustomerPrincipal(final Pageable pageable) {
		Page<Complaint> result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		result = this.complaintRepository.findByCustomerPrincipal(principal.getId(), pageable);
		Assert.notNull(result);

		return result;
	}

	public Page<Complaint> findNotSelfAssigned(final Pageable pageable) {
		Page<Complaint> result;

		result = this.complaintRepository.findNotSelfAssigned(pageable);
		Assert.notNull(result);

		return result;
	}

	public Page<Complaint> findSelfAssignedByPrincipal(final Pageable pageable) {
		Page<Complaint> result;
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		result = this.complaintRepository.findSelfAssignedByPrincipal(principal.getId(), pageable);
		Assert.notNull(result);

		return result;
	}

	public Page<Complaint> findInvolvedByHandyWorkerId(final int handyWorkerId, final Pageable pageable) {
		Page<Complaint> result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		result = this.complaintRepository.findInvolvedByHandyWorkerId(principal.getId(), pageable);
		Assert.notNull(result);

		return result;
	}

	protected Collection<String> findAllTickers() {
		Collection<String> result;

		result = this.complaintRepository.findAllTickers();
		Assert.notNull(result);

		return result;
	}

	protected Complaint findByReportId(final int reportId) {
		Complaint result;

		result = this.complaintRepository.findByReportId(reportId);
		Assert.notNull(result);

		return result;
	}

	protected void addReport(final Complaint complaint, final Report report) {
		complaint.setReport(report);
	}

	protected void removeReport(final Complaint complaint) {
		complaint.setReport(null);
	}

}
