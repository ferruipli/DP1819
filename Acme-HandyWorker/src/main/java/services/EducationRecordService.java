
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// Managed repository ------------------------------

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// Supporting services -----------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private CurriculumService			curriculumService;


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

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);
		Assert.isTrue(this.educationRecordRepository.exists(educationRecord.getId()));

		// Debemos de eliminar el educationRecord del curriculum del handyworker

		HandyWorker handyworker;
		Curriculum curriculum;

		handyworker = this.handyWorkerService.findByPrincipal();
		curriculum = handyworker.getCurriculum();
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getEducationRecords().contains(educationRecord));

		// Eliminamos el EducationRecord del curriculum del handyworker Principal

		this.curriculumService.removeEducationRecord(curriculum, educationRecord);

		// Eliminamos definitivamente el education record

		this.educationRecordRepository.delete(educationRecord);
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(!(this.educationRecordRepository.exists(educationRecord.getId())));

		EducationRecord result;
		HandyWorker handyWorker;
		Curriculum curriculum;

		result = this.educationRecordRepository.save(educationRecord);

		handyWorker = this.handyWorkerService.findByPrincipal();
		curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum);

		this.curriculumService.addEducationRecord(curriculum, result);

		return result;

	}

	// Other business methods --------------------------

}
