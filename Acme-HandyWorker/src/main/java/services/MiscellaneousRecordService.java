
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository ------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services -----------------------------

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private CurriculumService				curriculumService;


	// Constructors ------------------------------------

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public MiscellaneousRecord create() {
		MiscellaneousRecord result;

		result = new MiscellaneousRecord();

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		Assert.isTrue(miscellaneousRecordId != 0);

		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Assert.notNull(result);

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);
		Assert.isTrue(this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()));

		// Debemos de eliminar el miscellaneousRecord del curriculum del handyworker

		HandyWorker handyworker;
		Curriculum curriculum;

		handyworker = this.handyWorkerService.findByPrincipal();
		curriculum = handyworker.getCurriculum();
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(miscellaneousRecord));

		// Eliminamos el MiscellaneousRecord del curriculum del handyworker Principal

		this.curriculumService.removeMiscellaneousRecord(curriculum, miscellaneousRecord);

		// Eliminamos definitivamente el miscellaneousRecord

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods --------------------------

}
