
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository ------------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services -----------------------------
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

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> results;

		results = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(results);

		return results;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		if (this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId()))
			this.checkByPrincipal(miscellaneousRecord);
		else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			this.curriculumService.addMiscellaneousRecord(curriculum, result);
		}
		return result;

	}
	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);
		this.checkByPrincipal(miscellaneousRecord);

		// We must delete handyworker's miscellaneous record
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// We delete the miscellaneous record of handyworker's logged curriculum
		this.curriculumService.removeMiscellaneousRecord(curriculum, miscellaneousRecord);

		// We delete the miscellaneous record of the system
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final MiscellaneousRecord miscellaneousRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(miscellaneousRecord));
	}

}
