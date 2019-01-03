
package services;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private UtilityService		utilityService;


	// Constructor ------------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Report create() {
		Report result;

		result = new Report();
		result.setNotes(Collections.<Note> emptySet());
		result.setMoment(this.utilityService.current_moment());

		return result;
	}

	public Report save(final Report report) { // Updating
		return this.save(null, report);
	}

	public Report save(Complaint complaint, final Report report) { // Creating
		Assert.notNull(report);
		this.utilityService.checkAttachments(report.getAttachments());

		boolean isUpdating;
		Report result;
		Referee principal;

		isUpdating = this.reportRepository.exists(report.getId());
		principal = this.refereeService.findByPrincipal();

		if (isUpdating) {
			Assert.isTrue(!report.getFinalMode());
			complaint = this.complaintService.findByReportId(report.getId());
		}

		Assert.notNull(complaint);
		this.utilityService.checkActorIsBanned(principal);
		this.utilityService.checkIsSpamMarkAsSuspicious(report.getAttachments() + report.getDescription(), principal);
		this.utilityService.checkDate(complaint.getMoment(), report.getMoment());
		Assert.isTrue(this.refereeService.principalHasSelfAssigned(complaint));
		result = this.reportRepository.save(report);

		if (!isUpdating)
			this.complaintService.addReport(complaint, result);

		return result;
	}

	public void delete(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(this.reportRepository.exists(report.getId()));
		Assert.isTrue(!report.getFinalMode());

		Complaint complaintInvolved;
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		complaintInvolved = this.complaintService.findByReportId(report.getId());

		this.utilityService.checkActorIsBanned(principal);
		Assert.isTrue(this.refereeService.principalHasSelfAssigned(complaintInvolved));

		this.complaintService.removeReport(complaintInvolved);
		this.reportRepository.delete(report);
	}

	public Report findOne(final int reportId) {
		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Integer findIdByNoteId(final int noteId) {
		Integer result;

		result = this.reportRepository.findIdByNoteId(noteId);

		return result;
	}

	public boolean isPrincipalCreator(final Report report) {
		Referee principal, creator;
		boolean res;

		principal = this.refereeService.findByPrincipal();
		creator = this.refereeService.findByReportId(report.getId());
		this.utilityService.checkActorIsBanned(principal);

		res = creator.equals(principal);

		return res;
	}

	public void makeFinal(final Report report) {
		Assert.isTrue(this.isPrincipalCreator(report));
		report.setFinalMode(true);
	}

	protected Report findByNoteId(final int noteId) {
		Report result;

		result = this.reportRepository.findByNoteId(noteId);

		return result;
	}

	protected void addNote(final Report report, final Note note) {
		report.getNotes().add(note);
	}
}
