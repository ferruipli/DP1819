
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
		this.utilityService.checkDate(professionalRecord.getStartDate(), professionalRecord.getEndDate());

		ProfessionalRecord result;

		if (this.professionalRecordRepository.exists(professionalRecord.getId())) {
			this.checkByPrincipal(professionalRecord);

			result = this.professionalRecordRepository.save(professionalRecord);
		} else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			result = this.professionalRecordRepository.save(professionalRecord);

			this.curriculumService.addProfessionalRecord(curriculum, result);
		}

		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);
		this.checkByPrincipal(professionalRecord);

		// Debemos de eliminar el professionalRecord del curriculum del handyworker
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// Eliminamos el ProfessionalRecord del curriculum del handyworker Principal
		this.curriculumService.removeProfessionalRecord(curriculum, professionalRecord);

		// Eliminamos definitivamente el ProfessionalRecord
		this.professionalRecordRepository.delete(professionalRecord);
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final ProfessionalRecord professionalRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getProfessionalRecords().contains(professionalRecord));
	}

}
