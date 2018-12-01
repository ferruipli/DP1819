
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository ------------------------------

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services -----------------------------
	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private UtilityService				utilityService;


	// Constructors ------------------------------------

	public EducationRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public EducationRecord create() {
		EducationRecord result;

		result = new EducationRecord();

		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);

		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> results;

		results = this.educationRecordRepository.findAll();
		Assert.notNull(results);

		return results;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		this.utilityService.checkDate(educationRecord.getStartDate(), educationRecord.getEndDate());

		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);

		// If it exists, updates
		if (this.educationRecordRepository.exists(educationRecord.getId()))
			this.checkByPrincipal(educationRecord);
		else {
			// else, it creates

			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			this.curriculumService.addEducationRecord(curriculum, result);
		}

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);
		this.checkByPrincipal(educationRecord);

		// Debemos de eliminar el educationRecord del curriculum del handyworker
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// Eliminamos el EducationRecord del curriculum del handyworker Principal
		this.curriculumService.removeEducationRecord(curriculum, educationRecord);

		// Eliminamos definitivamente el education record
		this.educationRecordRepository.delete(educationRecord);
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final EducationRecord educationRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getEducationRecords().contains(educationRecord));
	}

}
