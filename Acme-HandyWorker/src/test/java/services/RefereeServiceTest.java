
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
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private RefereeService		refereeService;

	// Supporting services/repositories ---------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Test -------------------------------------------------------------------

	@Test
	public void testSaveReferee() {
		Referee referee, saved;

		referee = this.refereeService.create();
		referee.setName("Juan");
		referee.setAddress("Calle Test");
		referee.setEmail("juan <juantest@gmail.com>");
		referee.setMiddleName("Master");
		referee.setPhoneNumber("+42 123123123");
		referee.setPhotoLink("https://imagetest.com/12g5hr45");
		referee.setSurname("López");
		referee.getUserAccount().setUsername("noombreusuario");

		saved = this.refereeService.save(referee);
		Assert.isTrue(this.refereeService.findOne(saved.getId()).equals(saved));
	}

	@Test
	public void testUpdateReferee() {
		Referee referee, saved;
		int numberOfComplaints;
		String nameChanged;

		nameChanged = "Test name";
		referee = this.refereeService.findOne(super.getEntityId("referee1"));
		numberOfComplaints = referee.getComplaints().size();

		super.authenticate("referee1");

		referee.setName(nameChanged);
		saved = this.refereeService.save(referee);

		Assert.isTrue(numberOfComplaints == saved.getComplaints().size());
		Assert.isTrue(referee.getName().equals(nameChanged));

		super.unauthenticate();
	}

	@Test
	public void selfAssignComplaint() {
		int refereeId, complaintsSelfAssigned1, complaintsSelfAssigned2;
		Referee referee1, referee2;
		Complaint complaint;

		super.authenticate("customer4");
		complaint = this.createSimpleComplaint();
		super.unauthenticate();

		refereeId = super.getEntityId("referee1");

		referee1 = this.refereeService.findOne(refereeId);
		complaintsSelfAssigned1 = referee1.getComplaints().size();

		super.authenticate("referee1");
		this.refereeService.selfAssignComplaint(complaint);
		super.unauthenticate();

		referee2 = this.refereeService.findOne(refereeId);
		complaintsSelfAssigned2 = referee2.getComplaints().size();

		Assert.isTrue(complaintsSelfAssigned1 == complaintsSelfAssigned2 - 1);
	}

	// Ancillary methods ------------------------------------------------------

	private Complaint createSimpleComplaint() {
		Complaint complaint, saved;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask6"));

		complaint = this.complaintService.create();
		complaint.setFixUpTask(fixUpTask);
		complaint.setAttachments("");
		saved = this.complaintService.save(complaint);

		return saved;
	}
}
