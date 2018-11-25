
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Complaint;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private ReportService		reportService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;


	// Test -------------------------------------------------------------------

	@Test
	public void testSaveDeleteReport() {
		Report report, saved;
		int complaintId;
		Complaint complaint;

		complaintId = super.getEntityId("complaint1");

		super.authenticate("referee1");

		report = this.reportService.create();
		report.setDescription("Esto es una descripción de prueba");

		saved = this.reportService.writeNewReport(complaintId, report);
		complaint = this.complaintService.findOne(complaintId);
		Assert.isTrue(complaint.getReport().equals(saved));

		this.reportService.delete(saved);
		complaint = this.complaintService.findOne(complaintId);
		Assert.isTrue(complaint.getReport() == null);

		super.unauthenticate();
	}

	@Test
	public void testUpdateReport() {
		Report report, saved;
		Complaint complaint;
		String description;

		description = "Esto es una descripción de prueba modificada";
		report = this.reportService.findOne(super.getEntityId("report1"));

		super.authenticate("referee1");

		report.setDescription(description);

		saved = this.reportService.update(report);
		complaint = this.complaintService.findByReportId(saved.getId());
		Assert.isTrue(complaint.getReport().getDescription().equals(description));

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNotOwnedReport() {
		Report report, saved;
		Complaint complaint;
		String description;

		description = "Esto es una descripción de prueba modificada";
		report = this.reportService.findOne(super.getEntityId("report3"));

		super.authenticate("referee1");

		report.setDescription(description);

		saved = this.reportService.update(report);
		complaint = this.complaintService.findByReportId(saved.getId());
		Assert.isTrue(complaint.getReport().getDescription().equals(description));

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNotOwnedReport() {
		Report report;
		Complaint complaint;
		int complaintId;

		report = this.reportService.findOne(super.getEntityId("report3"));
		complaintId = this.complaintService.findByReportId(report.getId()).getId();

		super.authenticate("referee1");

		this.reportService.delete(report);
		complaint = this.complaintService.findOne(complaintId);
		Assert.isTrue(complaint.getReport() == null);

		super.unauthenticate();
	}
}
