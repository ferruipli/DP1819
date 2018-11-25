
package services;

import java.util.Collection;
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
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Report();
		result.setMoment(moment);
		result.setNotes(Collections.<Note> emptySet());

		return result;
	}

	public Report update(final Report report) {
		Report result;
		Complaint complaintInvolved;

		Assert.isTrue(this.reportRepository.exists(report.getId()));
		Assert.isTrue(!report.getFinalMode());
		complaintInvolved = this.complaintService.findByReportId(report.getId());
		this.checkRefPrincipalHandles(complaintInvolved);

		result = this.reportRepository.save(report);

		return result;
	}

	public void delete(final Report report) {
		Complaint complaintInvolved;

		Assert.isTrue(this.reportRepository.exists(report.getId()));
		Assert.isTrue(!report.getFinalMode());
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

	public Report writeNewReport(final int complaintId, final Report report) {
		Report result;
		Complaint complaint;

		Assert.isTrue(!this.reportRepository.exists(report.getId()));
		complaint = this.complaintService.findOne(complaintId);
		this.checkRefPrincipalHandles(complaint);

		result = this.reportRepository.save(report);

		this.complaintService.addReport(complaint, result);

		return result;
	}

	public Collection<Report> findByRefereeId(final int refereeId) {
		Collection<Report> result;

		result = this.reportRepository.findByRefereeId(refereeId);
		Assert.notNull(result);

		return result;
	}

	public Report findByNoteId(final int noteId) {
		Report result;

		result = this.reportRepository.findByNoteId(noteId);
		Assert.notNull(result);

		return result;
	}

	private void checkRefPrincipalHandles(final Complaint complaintInvolved) {
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getComplaints().contains(complaintInvolved));
	}

	protected void addNote(final Report report, final Note note) {
		report.getNotes().add(note);
	}
}
