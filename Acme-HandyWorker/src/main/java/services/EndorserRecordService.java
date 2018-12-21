
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

	protected Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> results;

		results = this.endorserRecordRepository.findAll();
		Assert.notNull(results);

		return results;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		this.utilityService.checkEmailRecords(endorserRecord.getEmail());

		final EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);

		if (this.endorserRecordRepository.exists(endorserRecord.getId()))
			this.checkByPrincipal(endorserRecord);
		else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			this.curriculumService.addEndorserRecord(curriculum, result);
		}

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);
		this.checkByPrincipal(endorserRecord);

		// We must delete handyworker's endorser record
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// We delete the endorser record of handyworker's logged curriculum
		this.curriculumService.removeEndorserRecord(curriculum, endorserRecord);

		// We delete the endorser record of the system
		this.endorserRecordRepository.delete(endorserRecord);
	}

	// Other business methods --------------------------
	private void checkByPrincipal(final EndorserRecord endorserRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getEndorserRecords().contains(endorserRecord));
	}

}
