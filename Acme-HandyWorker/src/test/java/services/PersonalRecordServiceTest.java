
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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	//Service under test ----------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;


	//Test ------------------------------------------------

	@Test
	public void testCreatePersonalRecord() {
		super.authenticate("handyworker1");
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();

		Assert.notNull(personalRecord);
		Assert.isNull(personalRecord.getPhotoLink());
		Assert.isNull(personalRecord.getEmail());
		Assert.isNull(personalRecord.getPhoneNumber());
		Assert.isNull(personalRecord.getLinkedInProfile());

	}

	@Test
	public void testSavePersonalRecord() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> personalRecords;
		String fullName, photoLink, email, phoneNumber, linkedInProfile;

		super.authenticate("handyworker3");
		personalRecord = this.personalRecordService.create();

		fullName = "Jesús";
		photoLink = "http://www.instagram.com";
		email = "jesus@gmail.com";
		phoneNumber = "954301287";
		linkedInProfile = "http://www.linkedin.com";

		personalRecord.setFullName(fullName);
		personalRecord.setPhotoLink(photoLink);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setLinkedInProfile(linkedInProfile);

		saved = this.personalRecordService.save(personalRecord);

		personalRecords = this.personalRecordService.findAll();

		Assert.isTrue(personalRecords.contains(saved));

		super.authenticate(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveWrongEmailPersonalRecord() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> personalRecords;
		String fullName, photoLink, email, phoneNumber, linkedInProfile;

		super.authenticate("handyworker1");
		personalRecord = this.personalRecordService.create();

		fullName = "Jesús";
		photoLink = "http://www.instagram.com";
		email = "Jesus <jesus@>";
		phoneNumber = "954301287";
		linkedInProfile = "http://www.linkedin.com";

		personalRecord.setFullName(fullName);
		personalRecord.setPhotoLink(photoLink);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setLinkedInProfile(linkedInProfile);

		saved = this.personalRecordService.save(personalRecord);

		personalRecords = this.personalRecordService.findAll();

		Assert.isTrue(personalRecords.contains(saved));

		super.authenticate(null);
	}

	// Save a personal record without login
	@Test(expected = IllegalArgumentException.class)
	public void testSavePersonalRecordNegative() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> personalRecords;
		String fullName, photoLink, email, phoneNumber, linkedInProfile;

		personalRecord = this.personalRecordService.create();

		fullName = "Jesús";
		photoLink = "http://www.instagram.com";
		email = "jesus@gmail.com";
		phoneNumber = "954301287";
		linkedInProfile = "http://www.linkedin.com";

		personalRecord.setFullName(fullName);
		personalRecord.setPhotoLink(photoLink);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setLinkedInProfile(linkedInProfile);

		saved = this.personalRecordService.save(personalRecord);

		personalRecords = this.personalRecordService.findAll();

		Assert.isTrue(personalRecords.contains(saved));

	}

	// Save a personal record without a handyworker account.
	@Test(expected = NullPointerException.class)
	public void testSavePersonalRecordNegative2() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> personalRecords;
		String fullName, photoLink, email, phoneNumber, linkedInProfile;

		super.authenticate("customer1");
		personalRecord = this.personalRecordService.create();

		fullName = "Jesús";
		photoLink = "http://www.instagram.com";
		email = "jesus@gmail.com";
		phoneNumber = "954301287";
		linkedInProfile = "http://www.linkedin.com";

		personalRecord.setFullName(fullName);
		personalRecord.setPhotoLink(photoLink);
		personalRecord.setEmail(email);
		personalRecord.setPhoneNumber(phoneNumber);
		personalRecord.setLinkedInProfile(linkedInProfile);

		saved = this.personalRecordService.save(personalRecord);

		personalRecords = this.personalRecordService.findAll();

		Assert.isTrue(personalRecords.contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testUpdatePersonalRecord() {
		super.authenticate("handyworker2");

		PersonalRecord personalRecord;
		int id;
		String newFullName, lastFullName;

		id = super.getEntityId("personalRecord1");
		newFullName = "Nuevo name";

		personalRecord = this.personalRecordService.findOne(id);
		lastFullName = personalRecord.getFullName();

		personalRecord.setFullName(newFullName);

		this.personalRecordService.save(personalRecord);

		Assert.isTrue(!lastFullName.equals(personalRecord.getFullName()));

		super.unauthenticate();
	}

}
