
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curriculum;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository ------------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services -----------------------------
	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private UtilityService				utilityService;


	// Constructors ------------------------------------

	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public EndorserRecord create() {
		EndorserRecord result;

		result = new EndorserRecord();

		return result;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);

		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.notNull(result);

		return result;
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> results;

		results = this.endorserRecordRepository.findAll();

		return results;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		this.utilityService.checkEmailRecords(endorserRecord.getEmail());

		final EndorserRecord result;

		if (this.endorserRecordRepository.exists(endorserRecord.getId())) {
			this.checkByPrincipal(endorserRecord);

			result = this.endorserRecordRepository.save(endorserRecord);
		} else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			result = this.endorserRecordRepository.save(endorserRecord);

			this.curriculumService.addEndorserRecord(curriculum, result);
		}

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);
		this.checkByPrincipal(endorserRecord);

		// Debemos de eliminar el endorserRecord del curriculum del handyworker
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// Eliminamos el EndorserRecord del curriculum del handyworker Principal
		this.curriculumService.removeEndorserRecord(curriculum, endorserRecord);

		// Eliminamos definitivamente el education record
		this.endorserRecordRepository.delete(endorserRecord);
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final EndorserRecord endorserRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getEndorserRecords().contains(endorserRecord));
	}

}
