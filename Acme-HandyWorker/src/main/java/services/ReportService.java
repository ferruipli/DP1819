
package services;

import java.util.Collections;
import java.util.Date;

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


	// Constructor ------------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Report create() {
		Report result;

		result = new Report();
		result.setNotes(Collections.<Note> emptySet());

		return result;
	}

	public Report writeNewReport(final int complaintId, final Report report) {
		Assert.isTrue(!this.reportRepository.exists(report.getId()));

		Report result;
		Complaint complaint;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		complaint = this.complaintService.findOne(complaintId);
		Assert.isTrue(moment.after(complaint.getMoment()));
		this.checkRefPrincipalHandles(complaint);

		report.setMoment(moment);
		result = this.reportRepository.save(report);

		this.complaintService.addReport(complaint, result);

		return result;
	}

	public Report update(final Report report) {
		Assert.isTrue(this.reportRepository.exists(report.getId()));
		Assert.isTrue(!report.getFinalMode());

		Report result;
		Complaint complaintInvolved;

		complaintInvolved = this.complaintService.findByReportId(report.getId());
		Assert.isTrue(report.getMoment().after(complaintInvolved.getMoment()));
		this.checkRefPrincipalHandles(complaintInvolved);

		result = this.reportRepository.save(report);

		return result;
	}

	public void delete(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(this.reportRepository.exists(report.getId()));
		Assert.isTrue(!report.getFinalMode());

		Complaint complaintInvolved;

		complaintInvolved = this.complaintService.findByReportId(report.getId());
		this.checkRefPrincipalHandles(complaintInvolved);

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

	public Report findByNoteId(final int noteId) {
		Report result;

		result = this.reportRepository.findByNoteId(noteId);
		Assert.notNull(result);

		return result;
	}

	protected void addNote(final Report report, final Note note) {
		report.getNotes().add(note);
	}

	private void checkRefPrincipalHandles(final Complaint complaintInvolved) {
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		Assert.isTrue(principal.getComplaints().contains(complaintInvolved));
	}
}
