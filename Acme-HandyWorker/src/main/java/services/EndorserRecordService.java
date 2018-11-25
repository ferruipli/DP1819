
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository ------------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// Supporting services -----------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

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
		Assert.isTrue(!(this.endorserRecordRepository.exists(endorserRecord.getId())));
		this.utilityService.checkEmailRecords(endorserRecord.getEmail());

		HandyWorker handyWorker;
		final EndorserRecord result;
		Curriculum curriculum;

		result = this.endorserRecordRepository.save(endorserRecord);

		handyWorker = this.handyWorkerService.findByPrincipal();
		curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum);

		this.curriculumService.addEndorserRecord(curriculum, result);

		return result;

	}
	// Other business methods --------------------------
}
