
package services;

import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	//Service under test ----------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	//Test ------------------------------------------------

	@Test
	public void testCreateProfessionalRecord() {
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.create();

		Assert.notNull(professionalRecord);
		Assert.isNull(professionalRecord.getNameCompany());
		Assert.isNull(professionalRecord.getStartDate());
		Assert.isNull(professionalRecord.getEndDate());
		Assert.isNull(professionalRecord.getRole());
		Assert.isNull(professionalRecord.getAttachment());
		Assert.isNull(professionalRecord.getComments());
	}

	@Test
	public void testSaveProfessionalRecord() {
		final ProfessionalRecord professionalRecord, saved;
		final Collection<ProfessionalRecord> professionalRecords;
		final String nameCompany, role, attachment, comments;
		final Date startDate, endDate;

		super.authenticate("handyworker2");
		professionalRecord = this.professionalRecordService.create();

		nameCompany = "Telefonica";
		role = "Developer";
		startDate = LocalDate.parse("2017-01-01").toDate();
		endDate = LocalDate.parse("2018-01-01").toDate();
		attachment = "http://wwww.telefonica.com";
		comments = "comment";

		professionalRecord.setNameCompany(nameCompany);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole(role);
		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);

		saved = this.professionalRecordService.save(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(saved));

		super.authenticate(null);

	}

	//Save a professional record without login
	@Test(expected = IllegalArgumentException.class)
	public void testSaveProfessionalRecordNegative() {
		final ProfessionalRecord professionalRecord, saved;
		final Collection<ProfessionalRecord> professionalRecords;
		final String nameCompany, role, attachment, comments;
		final Date startDate, endDate;

		professionalRecord = this.professionalRecordService.create();

		nameCompany = "Telefonica";
		role = "Developer";
		startDate = LocalDate.parse("2017-01-01").toDate();
		endDate = LocalDate.parse("2018-01-01").toDate();
		attachment = "http://wwww.telefonica.com";
		comments = "comment";

		professionalRecord.setNameCompany(nameCompany);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole(role);
		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);

		saved = this.professionalRecordService.save(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(saved));

	}

	//Save a professional record without a handyworker account.
	@Test(expected = NullPointerException.class)
	public void testSaveProfessionalRecordNegative2() {
		final ProfessionalRecord professionalRecord, saved;
		final Collection<ProfessionalRecord> professionalRecords;
		final String nameCompany, role, attachment, comments;
		final Date startDate, endDate;

		super.authenticate("customer1");
		professionalRecord = this.professionalRecordService.create();

		nameCompany = "Telefonica";
		role = "Developer";
		startDate = LocalDate.parse("2017-01-01").toDate();
		endDate = LocalDate.parse("2018-01-01").toDate();
		attachment = "http://wwww.telefonica.com";
		comments = "comment";

		professionalRecord.setNameCompany(nameCompany);
		professionalRecord.setStartDate(startDate);
		professionalRecord.setEndDate(endDate);
		professionalRecord.setRole(role);
		professionalRecord.setAttachment(attachment);
		professionalRecord.setComments(comments);

		saved = this.professionalRecordService.save(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testUpdateProfessionalRecord() {
		super.authenticate("handyworker2");

		ProfessionalRecord professionalRecord;
		int id;
		String newNameCompany, lastNameCompany;

		id = super.getEntityId("professionalRecord1");
		newNameCompany = "Nuevo company";

		professionalRecord = this.professionalRecordService.findOne(id);
		lastNameCompany = professionalRecord.getNameCompany();

		professionalRecord.setNameCompany(newNameCompany);

		this.professionalRecordService.save(professionalRecord);

		Assert.isTrue(!lastNameCompany.equals(professionalRecord.getNameCompany()));

		super.unauthenticate();
	}

	@Test
	public void testDeleteProfessionalRecord() {
		ProfessionalRecord professionalRecord;
		Collection<ProfessionalRecord> professionalRecords;

		super.authenticate("handyworker2");
		professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(professionalRecord));

		this.professionalRecordService.delete(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(!(professionalRecords.contains(professionalRecord)));

		super.authenticate(null);

	}

	//Delete a professional record without login.
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteProfessionalRecordNegative() {
		ProfessionalRecord professionalRecord;
		Collection<ProfessionalRecord> professionalRecords;

		professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(professionalRecord));

		this.professionalRecordService.delete(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(!(professionalRecords.contains(professionalRecord)));

	}

	// Delete a professional record without a handyworker account.
	@Test(expected = NullPointerException.class)
	public void testDeleteProfessionalRecordNegative2() {
		ProfessionalRecord professionalRecord;
		Collection<ProfessionalRecord> professionalRecords;

		super.authenticate("customer1");
		professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(professionalRecord));

		this.professionalRecordService.delete(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(!(professionalRecords.contains(professionalRecord)));

		super.authenticate(null);

	}

}
