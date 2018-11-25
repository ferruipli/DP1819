
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
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Report();
		result.setMoment(moment);
		result.setNotes(Collections.<Note> emptySet());

		return result;
	}

	// COMPLT: Este método sirve para actualizar un report pero no para insertarlo,
	// ya que al insertarlo complaintInvolved devolvería null --> cambiar 
	// navegabilidades
	public Report save(final Report report) {
		Report result;
		Complaint complaintInvolved;

		complaintInvolved = this.complaintService.findByReportId(report.getId());

		this.checkRefPrincipalHandles(complaintInvolved);
		Assert.isTrue(!report.getFinalMode());

		result = this.reportRepository.save(report);
		complaintInvolved.setReport(result);

		return result;
	}

	public void delete(final Report report) {
		Complaint complaintInvolved;

		complaintInvolved = this.complaintService.findByReportId(report.getId());

		this.checkRefPrincipalHandles(complaintInvolved);
		Assert.isTrue(!report.getFinalMode());

		complaintInvolved.setReport(null);
		this.reportRepository.delete(report);
	}

	public Report findOne(final int reportId) {
		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	private void checkRefPrincipalHandles(final Complaint complaintInvolved) {
		Referee principal;

		principal = this.refereeService.findByPrincipal();
		Assert.isTrue(principal.getComplaints().contains(complaintInvolved));
	}
}
