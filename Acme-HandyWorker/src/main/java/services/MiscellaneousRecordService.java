
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

		return results;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		MiscellaneousRecord result;

		if (this.miscellaneousRecordRepository.exists(miscellaneousRecord.getId())) {
			this.checkByPrincipal(miscellaneousRecord);

			result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		} else {
			Curriculum curriculum;

			curriculum = this.curriculumService.findByPrincipal();

			result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

			this.curriculumService.addMiscellaneousRecord(curriculum, result);
		}
		return result;

	}
	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);
		this.checkByPrincipal(miscellaneousRecord);

		// Debemos de eliminar el miscellaneousRecord del curriculum del handyworker
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		// Eliminamos el MiscellaneousRecord del curriculum del handyworker Principal
		this.curriculumService.removeMiscellaneousRecord(curriculum, miscellaneousRecord);

		// Eliminamos definitivamente el miscellaneousRecord
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final MiscellaneousRecord miscellaneousRecord) {
		Curriculum curriculum;

		curriculum = this.curriculumService.findByPrincipal();

		Assert.isTrue(curriculum.getMiscellaneousRecords().contains(miscellaneousRecord));
	}

}
