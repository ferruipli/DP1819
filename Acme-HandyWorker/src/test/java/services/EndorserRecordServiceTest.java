
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	//Service under test ----------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;


	//Test ------------------------------------------------

	@Test
	public void testCreateEndorserRecord() {
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();

		Assert.notNull(endorserRecord);
		Assert.isNull(endorserRecord.getFullName());
		Assert.isNull(endorserRecord.getEmail());
		Assert.isNull(endorserRecord.getPhoneNumber());
		Assert.isNull(endorserRecord.getLinkedInProfile());
		Assert.isNull(endorserRecord.getComments());
	}

	@Test
	public void testSaveEndorserRecord() {
		EndorserRecord endorserRecord, saved;
		Collection<EndorserRecord> endorserRecords;
		String fullName, email, phoneNumber, linkedInProfile, comments;

		super.authenticate("handyworker1");
		endorserRecord = this.endorserRecordService.create();

		fullName = "Evaristo";
		email = "evaristo@gmail.com";
		phoneNumber = "954780123";
		linkedInProfile = "http://www.linkedin.com";
		comments = "comments";

		endorserRecord.setFullName(fullName);
		endorserRecord.setEmail(email);
		endorserRecord.setPhoneNumber(phoneNumber);
		endorserRecord.setLinkedInProfile(linkedInProfile);
		endorserRecord.setComments(comments);

		saved = this.endorserRecordService.save(endorserRecord);

		endorserRecords = this.endorserRecordService.findAll();

		Assert.isTrue(endorserRecords.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testDeleteEndorserRecord() {
		EndorserRecord endorserRecord;
		Collection<EndorserRecord> endorserRecords;

		super.authenticate("handyworker1");

		endorserRecord = this.endorserRecordService.findOne(super.getEntityId("endorserRecord1"));
		endorserRecords = this.endorserRecordService.findAll();

		Assert.isTrue(endorserRecords.contains(endorserRecord));

		this.endorserRecordService.delete(endorserRecord);

		endorserRecords = this.endorserRecordService.findAll();

		Assert.isTrue(!(endorserRecords.contains(endorserRecord)));

		super.authenticate(null);

	}
}
