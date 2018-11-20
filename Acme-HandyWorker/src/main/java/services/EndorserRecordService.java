
package services;

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

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);
		Assert.isTrue(this.endorserRecordRepository.exists(endorserRecord.getId()));

		// Debemos de eliminar el endorserRecord del curriculum del handyworker

		HandyWorker handyworker;
		Curriculum curriculum;

		handyworker = this.handyWorkerService.findByPrincipal();
		curriculum = handyworker.getCurriculum();
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getEndorserRecords().contains(endorserRecord));

		// Eliminamos el EndorserRecord del curriculum del handyworker Principal

		this.curriculumService.removeEndorserRecord(curriculum, endorserRecord);

		// Eliminamos definitivamente el education record

		this.endorserRecordRepository.delete(endorserRecord);
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(!(this.endorserRecordRepository.exists(endorserRecord.getId())));

		HandyWorker handyWorker;
		EndorserRecord result;
		Curriculum curriculum;

		if (endorserRecord.getEmail().matches("A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+") || endorserRecord.getEmail().matches("[\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]"))
			result = this.endorserRecordRepository.save(endorserRecord);
		else
			throw new IllegalArgumentException();

		handyWorker = this.handyWorkerService.findByPrincipal();
		curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum);

		this.curriculumService.addEndorserRecord(curriculum, result);

		return result;

	}

	// Other business methods --------------------------
}
