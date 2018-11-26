
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
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	//Service under test ----------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	//Test ------------------------------------------------

	@Test
	public void testCreateEducationRecord() {
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();

		Assert.notNull(educationRecord);
		Assert.isNull(educationRecord.getTitleDiploma());
		Assert.isNull(educationRecord.getStartDate());
		Assert.isNull(educationRecord.getEndDate());
		Assert.isNull(educationRecord.getInstitution());
		Assert.isNull(educationRecord.getAttachment());
		Assert.isNull(educationRecord.getComments());
	}

	@Test
	public void testSaveEducationRecord() {
		final EducationRecord educationRecord, saved;
		final Collection<EducationRecord> educationRecords;
		final String titleDiploma, institution, attachment, comments;
		final Date startDate, endDate;

		super.authenticate("handyworker1");
		educationRecord = this.educationRecordService.create();

		titleDiploma = "English B1";
		institution = "University of Cambridge";
		startDate = LocalDate.parse("2017-01-01").toDate();
		endDate = LocalDate.parse("2018-01-01").toDate();
		attachment = "http://wwww.cambridge.com";
		comments = "comment";

		educationRecord.setTitleDiploma(titleDiploma);
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setInstitution(institution);
		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);

		saved = this.educationRecordService.save(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testUpdateEducationRecord() {
		EducationRecord educationRecord;
		int id;
		String newTitleDiploma, lastTitleDiploma;

		id = super.getEntityId("educationRecord1");
		newTitleDiploma = "Nuevo title diploma";

		educationRecord = this.educationRecordService.findOne(id);
		lastTitleDiploma = educationRecord.getTitleDiploma();

		educationRecord.setTitleDiploma(newTitleDiploma);

		this.educationRecordService.save(educationRecord);

		Assert.isTrue(!lastTitleDiploma.equals(educationRecord.getTitleDiploma()));

	}

	// Usuario sin autenticar
	@Test(expected = IllegalArgumentException.class)
	public void testSaveEducationRecordNegative() {
		final EducationRecord educationRecord, saved;
		final Collection<EducationRecord> educationRecords;
		final String titleDiploma, institution, attachment, comments;
		final Date startDate, endDate;

		educationRecord = this.educationRecordService.create();

		titleDiploma = "English B1";
		institution = "University of Cambridge";
		startDate = LocalDate.parse("2017-01-01").toDate();
		endDate = LocalDate.parse("2018-01-01").toDate();
		attachment = "http://wwww.cambridge.com";
		comments = "comment";

		educationRecord.setTitleDiploma(titleDiploma);
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setInstitution(institution);
		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);

		saved = this.educationRecordService.save(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(saved));

	}

	// El usuario que guarda el education record es un customer
	@Test(expected = NullPointerException.class)
	public void testSaveEducationRecordNegative2() {
		final EducationRecord educationRecord, saved;
		final Collection<EducationRecord> educationRecords;
		final String titleDiploma, institution, attachment, comments;
		final Date startDate, endDate;

		super.authenticate("customer1");
		educationRecord = this.educationRecordService.create();

		titleDiploma = "English B1";
		institution = "University of Cambridge";
		startDate = LocalDate.parse("2017-01-01").toDate();
		endDate = LocalDate.parse("2018-01-01").toDate();
		attachment = "http://wwww.cambridge.com";
		comments = "comment";

		educationRecord.setTitleDiploma(titleDiploma);
		educationRecord.setStartDate(startDate);
		educationRecord.setEndDate(endDate);
		educationRecord.setInstitution(institution);
		educationRecord.setAttachment(attachment);
		educationRecord.setComments(comments);

		saved = this.educationRecordService.save(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testDeleteEducationRecord() {
		EducationRecord educationRecord;
		Collection<EducationRecord> educationRecords;

		super.authenticate("handyworker1");
		educationRecord = this.educationRecordService.findOne(super.getEntityId("educationRecord1"));
		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(educationRecord));

		this.educationRecordService.delete(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(!(educationRecords.contains(educationRecord)));

		super.authenticate(null);

	}

	// Eliminar un education record sin autenticarse
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteEducationRecordNegative() {
		EducationRecord educationRecord;
		Collection<EducationRecord> educationRecords;

		educationRecord = this.educationRecordService.findOne(super.getEntityId("educationRecord1"));
		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(educationRecord));

		this.educationRecordService.delete(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(!(educationRecords.contains(educationRecord)));

	}

}
