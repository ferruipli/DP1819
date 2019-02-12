
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
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Test -------------------------------------------------------------------

	@Test
	public void testSaveComplaint() {
		Complaint complaint, saved;
		FixUpTask fixUpTask;
		int complaintId;

		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask7"));

		super.authenticate("customer6");

		complaint = this.complaintService.create();
		complaint.setDescription("Esto es una queja de test");
		complaint.setAttachments("http://www.test1.com\rhttp://www.test2.com");
		complaint.setFixUpTask(fixUpTask);

		saved = this.complaintService.save(complaint);
		complaintId = saved.getId();

		super.unauthenticate();

		Assert.isTrue(saved.equals(this.complaintService.findOne(complaintId)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveComplaintNotOwned() {
		Complaint complaint, saved;
		FixUpTask fixUpTask;
		int complaintId;

		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask7"));

		super.authenticate("customer1");

		complaint = this.complaintService.create();
		complaint.setDescription("Esto es una queja de test");
		complaint.setAttachments("http://www.test1.com\rhttp://www.test2.com");
		complaint.setFixUpTask(fixUpTask);

		saved = this.complaintService.save(complaint);
		complaintId = saved.getId();

		super.unauthenticate();

		Assert.isTrue(saved.equals(this.complaintService.findOne(complaintId)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateComplaint() {
		Complaint complaint;

		super.authenticate("customer6");

		complaint = this.complaintService.findOne(super.getEntityId("complaint1"));
		complaint.setDescription("Esta descripción ha sido modificada en el test");
		complaint.setAttachments("http://www.test1.com\rhttp://www.test2.com");
		this.complaintService.save(complaint);

		super.unauthenticate();
	}
}
