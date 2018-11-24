
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services -----------------------------

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private CurriculumService				curriculumService;


	// Constructors ------------------------------------

	public ProfessionalRecordService() {
		super();
	}
	// Simple CRUD methods -----------------------------

	public ProfessionalRecord create() {
		ProfessionalRecord result;

		result = new ProfessionalRecord();

		return result;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		Assert.isTrue(professionalRecordId != 0);
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.notNull(result);

		return result;

	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> results;

		results = this.professionalRecordRepository.findAll();

		return results;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(!(this.professionalRecordRepository.exists(professionalRecord.getId())));

		HandyWorker handyWorker;
		Curriculum curriculum;
		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(professionalRecord);

		handyWorker = this.handyWorkerService.findByPrincipal();
		curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum);

		this.curriculumService.addProfessionalRecord(curriculum, result);

		return result;
	}

	// Other business methods --------------------------

}
