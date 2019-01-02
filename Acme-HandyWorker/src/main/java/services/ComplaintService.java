
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
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
	private ApplicationService	applicationService;

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
		result.setMoment(this.utilityService.current_moment());

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		Assert.isTrue(!this.complaintRepository.exists(complaint.getId())); // Complaints cannot be updated
		Assert.notNull(this.applicationService.findAcceptedApplication(complaint.getFixUpTask().getId()));
		this.utilityService.checkAttachments(complaint.getAttachments());

		Complaint result;
		Customer principal;

		principal = this.customerService.findByPrincipal();
		Assert.isTrue(complaint.getFixUpTask().getCustomer().equals(principal));
		this.utilityService.checkIsSpamMarkAsSuspicious(complaint.getAttachments() + complaint.getDescription(), principal);

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

	public Integer findIdByReportId(final int reportId) {
		Integer result;

		result = this.complaintRepository.findIdByReportId(reportId);

		return result;
	}

	public boolean isAssigned(final int complaintId) {
		return this.complaintRepository.findAssigned(complaintId) != null;
	}

	public Page<Complaint> findByFixUpTaskId(final int fixUpTaskId, final Pageable pageable) {
		Page<Complaint> result;

		result = this.complaintRepository.findByFixUpTaskId(fixUpTaskId, pageable);
		Assert.notNull(result);

		return result;
	}

	public Page<Complaint> findNotAssigned(final Pageable pageable) {
		Page<Complaint> result;

		result = this.complaintRepository.findNotAssigned(pageable);
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

	public Page<Complaint> findInvolvedByHandyWorkerPrincipal(final Pageable pageable) {
		Page<Complaint> result;
		HandyWorker principal;

		principal = this.handyWorkerService.findByPrincipal();
		result = this.complaintRepository.findInvolvedByHandyWorkerId(principal.getId(), pageable);
		Assert.notNull(result);

		return result;
	}

	public Double[] findDataNumberComplaintPerFixUpTask() {
		Double[] result;

		result = this.complaintRepository.findDataNumberComplaintPerFixUpTask();

		return result;
	}

	public void addFixUpTask(final Complaint complaint, final FixUpTask fixUpTask) {
		complaint.setFixUpTask(fixUpTask);
	}

	protected String existTicker(final String ticker) {
		String result;

		result = this.complaintRepository.existTicker(ticker);

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
