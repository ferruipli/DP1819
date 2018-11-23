
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
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	//Service under test ----------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	// Supporting services ---------------------------------

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private PersonalRecordService		personalRecordService;


	// Test ------------------------------------------------

	@Test
	public void testCreateCurriculum() {
		Curriculum curriculum;

		curriculum = this.curriculumService.create();

		Assert.notNull(curriculum);

		Assert.notNull(curriculum.getTicker());
		Assert.notNull(curriculum.getEducationRecords());
		Assert.notNull(curriculum.getProfessionalRecords());
		Assert.notNull(curriculum.getEndorserRecords());
		Assert.notNull(curriculum.getMiscellaneousRecords());

	}

	@Test
	public void testSaveCurriculum() {
		final Curriculum curriculum, saved;
		//final Collection<Curriculum> curriculums;
		final Collection<EducationRecord> educationRecords;
		final Collection<ProfessionalRecord> professionalRecords;
		final Collection<EndorserRecord> endorserRecords;
		final Collection<MiscellaneousRecord> miscellaneousRecords;
		EducationRecord educationRecord;
		ProfessionalRecord professionalRecord;
		EndorserRecord endorserRecord;
		MiscellaneousRecord miscellaneousRecord;
		final PersonalRecord personalRecord;

		super.authenticate("handyworker1");

		curriculum = this.curriculumService.create();

		educationRecord = this.educationRecordService.findOne(super.getEntityId("educationRecord1"));
		professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		endorserRecord = this.endorserRecordService.findOne(super.getEntityId("endorserRecord1"));
		personalRecord = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		miscellaneousRecord = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));

		educationRecords = curriculum.getEducationRecords();
		endorserRecords = curriculum.getEndorserRecords();
		professionalRecords = curriculum.getProfessionalRecords();
		miscellaneousRecords = curriculum.getMiscellaneousRecords();

		Assert.isTrue(!educationRecords.contains(educationRecord));
		Assert.isTrue(!professionalRecords.contains(professionalRecord));
		Assert.isTrue(!endorserRecords.contains(endorserRecord));
		Assert.isTrue(!miscellaneousRecords.contains(miscellaneousRecord));

		this.curriculumService.addEducationRecord(curriculum, educationRecord);
		this.curriculumService.addEndorserRecord(curriculum, endorserRecord);
		this.curriculumService.addMiscellaneousRecord(curriculum, miscellaneousRecord);
		this.curriculumService.addProfessionalRecord(curriculum, professionalRecord);
		this.curriculumService.addPersonalRecord(curriculum, personalRecord);

		Assert.isTrue(curriculum.getEducationRecords().contains(educationRecord));
		Assert.isTrue(curriculum.getProfessionalRecords().contains(professionalRecord));
		Assert.isTrue(curriculum.getEndorserRecords().contains(endorserRecord));
		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(miscellaneousRecord));

		saved = this.curriculumService.save(curriculum);

		//curriculums = this.curriculumService.findAll();

		//Assert.isTrue(curriculums.contains(saved));

		Assert.notNull(this.curriculumService.findOne(saved.getId()));

		super.authenticate(null);

	}

	@Test
	public void testDeleteCurriculum() {
		Curriculum curriculum, deleted;
		int id;
		//final Curriculum deletedCurriculum;
		//Collection<Curriculum> curriculums;

		super.authenticate("handyworker1");

		id = super.getEntityId("curriculum1");
		System.out.println(id);
		curriculum = this.curriculumService.findOne(id);
		System.out.println(curriculum);
		//curriculums = this.curriculumService.findAll();

		//Assert.isTrue(curriculums.contains(curriculum));

		this.curriculumService.delete(curriculum);

		deleted = this.curriculumService.findOne(id);
		System.out.println(deleted);
		Assert.isNull(deleted);

		//curriculums = this.curriculumService.findAll();

		//Assert.isTrue(!(curriculums.contains(curriculum)));
		//Assert.isTrue(!this.curriculumRepository.exists(curriculum.getId()));

		super.authenticate(null);

	}

	@Test(expected = NullPointerException.class)
	public void testDeleteCurriculumByAnotherHandyWorker() {
		Curriculum curriculum, deleted;
		int id;
		//final Curriculum deletedCurriculum;
		//Collection<Curriculum> curriculums;

		super.authenticate("handyworker2");

		id = super.getEntityId("curriculum1");
		System.out.println(id);
		curriculum = this.curriculumService.findOne(id);
		System.out.println(curriculum);
		//curriculums = this.curriculumService.findAll();

		//Assert.isTrue(curriculums.contains(curriculum));

		this.curriculumService.delete(curriculum);

		deleted = this.curriculumService.findOne(id);
		System.out.println(deleted);
		Assert.isNull(deleted);

		//curriculums = this.curriculumService.findAll();

		//Assert.isTrue(!(curriculums.contains(curriculum)));
		//Assert.isTrue(!this.curriculumRepository.exists(curriculum.getId()));

		super.authenticate(null);

	}
}
