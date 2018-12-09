
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository ------------------------------
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// Supporting services -----------------------------
	@Autowired
	private CurriculumService				curriculumService;

	@Autowired
	private UtilityService					utilityService;


	// Constructors ------------------------------------

	public ProfessionalRecordService() {
		super();
	}
	// Simple CRUD methods -----------------------------

	protected ProfessionalRecord create() {
		ProfessionalRecord result;

		result = new ProfessionalRecord();

		return result;
	}

	protected ProfessionalRecord findOne(final int professionalRecordId) {
		Assert.isTrue(professionalRecordId != 0);
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(professionalRecordId);
		Assert.notNull(result);

		return result;

	}

	protected Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> results;

		results = this.professionalRecordRepository.findAll();
		Assert.notNull(results);

		return results;
	}

	protected ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		this.utilityService.checkDate(professionalRecord.getStartDate(), professionalRecord.getEndDate());

		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(professionalRecord);

		if (this.professionalRecordRepository.exists(professionalRecord.getId()))
			this.checkByPrincipal(professionalRecord);
		else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			this.curriculumService.addProfessionalRecord(curriculum, result);
		}

		return result;
	}

	protected void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);
		this.checkByPrincipal(professionalRecord);

		// We must delete handyworker's professional record
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// We delete the professional record of handyworker's logged curriculum
		this.curriculumService.removeProfessionalRecord(curriculum, professionalRecord);

		// We delete the professional record of the system
		this.professionalRecordRepository.delete(professionalRecord);
	}

	// Other business methods --------------------------
	private void checkByPrincipal(final ProfessionalRecord professionalRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getProfessionalRecords().contains(professionalRecord));
	}

}
