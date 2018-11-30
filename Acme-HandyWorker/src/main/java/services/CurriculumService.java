
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed repository ------------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services -----------------------------

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Constructors ------------------------------------
	public CurriculumService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Curriculum create() {
		Curriculum result;

		result = new Curriculum();

		result.setTicker(this.utilityService.generateValidTicker());
		result.setEducationRecords(new HashSet<EducationRecord>());
		result.setProfessionalRecords(new HashSet<ProfessionalRecord>());
		result.setEndorserRecords(new HashSet<EndorserRecord>());
		result.setMiscellaneousRecords(new HashSet<MiscellaneousRecord>());

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Assert.isTrue(curriculumId != 0);

		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);

		return result;

	}
	public Collection<Curriculum> findAll() {
		Collection<Curriculum> results;

		results = this.curriculumRepository.findAll();

		return results;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(handyWorker.getCurriculum().equals(curriculum));

		this.curriculumRepository.delete(curriculum);

		this.handyWorkerService.removeCurriculum(handyWorker, null);

	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(!(this.curriculumRepository.exists(curriculum.getId())));

		Curriculum result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();
		result = this.curriculumRepository.save(curriculum);
		this.handyWorkerService.addCurriculum(handyWorker, result);

		return result;
	}

	// Other business methods --------------------------
	public Curriculum findByPrincipal() {
		HandyWorker handyworker;
		Curriculum result;

		handyworker = this.handyWorkerService.findByPrincipal();
		result = handyworker.getCurriculum();

		Assert.notNull(result);

		return result;
	}

	public Boolean existCurriculumByTicker(final String ticker) {
		Boolean result;

		result = this.curriculumRepository.existCurriculumByTicker(ticker);

		return result;
	}

	public Collection<Curriculum> findAllCurriculums() {
		Collection<Curriculum> results;

		results = this.curriculumRepository.findAllCurriculums();

		return results;
	}

	public Collection<String> findAllTickers() {
		Collection<String> results;

		results = this.curriculumRepository.findAllTickers();

		return results;
	}

	protected void addEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		curriculum.getEducationRecords().add(educationRecord);
	}

	protected void removeEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		curriculum.getEducationRecords().remove(educationRecord);
	}

	protected void addEndorserRecord(final Curriculum curriculum, final EndorserRecord endorserRecord) {
		curriculum.getEndorserRecords().add(endorserRecord);
	}

	protected void removeEndorserRecord(final Curriculum curriculum, final EndorserRecord endorserRecord) {
		curriculum.getEndorserRecords().remove(endorserRecord);
	}

	protected void addMiscellaneousRecord(final Curriculum curriculum, final MiscellaneousRecord miscellaneousRecord) {
		curriculum.getMiscellaneousRecords().add(miscellaneousRecord);
	}

	protected void removeMiscellaneousRecord(final Curriculum curriculum, final MiscellaneousRecord miscellaneousRecord) {
		curriculum.getMiscellaneousRecords().remove(miscellaneousRecord);
	}

	protected void addPersonalRecord(final Curriculum curriculum, final PersonalRecord personalRecord) {
		curriculum.setPersonalRecord(personalRecord);
	}

	protected void addProfessionalRecord(final Curriculum curriculum, final ProfessionalRecord professionalRecord) {
		curriculum.getProfessionalRecords().add(professionalRecord);
	}

	protected void removeProfessionalRecord(final Curriculum curriculum, final ProfessionalRecord professionalRecord) {
		curriculum.getProfessionalRecords().remove(professionalRecord);
	}

}
