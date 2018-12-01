
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curriculum;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository ------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting services -----------------------------
	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private UtilityService				utilityService;


	// Constructors ------------------------------------

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public PersonalRecord create() {
		PersonalRecord result;

		result = new PersonalRecord();

		return result;
	}

	public PersonalRecord findOne(final int personalRecordId) {
		Assert.isTrue(personalRecordId != 0);
		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);
		Assert.notNull(result);

		return result;

	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> results;

		results = this.personalRecordRepository.findAll();
		Assert.notNull(results);

		return results;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		this.utilityService.checkEmailRecords(personalRecord.getEmail());

		PersonalRecord result;

		result = this.personalRecordRepository.save(personalRecord);

		if (this.personalRecordRepository.exists(personalRecord.getId()))
			this.checkByPrincipal(personalRecord);
		else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			this.curriculumService.addPersonalRecord(curriculum, result);
		}

		return result;
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final PersonalRecord personalRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getPersonalRecord().equals(personalRecord));
	}

}
