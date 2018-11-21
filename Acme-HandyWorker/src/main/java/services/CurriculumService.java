
package services;

import java.util.Collection;
import java.util.Collections;
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
	private CurriculumRepository		curriculumRepository;

	// Supporting services -----------------------------

	//@Autowired
	//private UtilityService				utilityService;

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;


	// Constructors ------------------------------------
	public CurriculumService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Curriculum create() {
		Curriculum result;

		result = new Curriculum();

		//result.setTicker(this.utilityService.generateValidTicker());
		result.setEducationRecords(Collections.<EducationRecord> emptySet());
		result.setProfessionalRecords(Collections.<ProfessionalRecord> emptySet());
		result.setEndorserRecords(Collections.<EndorserRecord> emptySet());
		result.setMiscellaneousRecords(Collections.<MiscellaneousRecord> emptySet());

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Assert.isTrue(curriculumId != 0);

		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;

	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);
		Assert.isTrue(this.curriculumRepository.exists(curriculum.getId()));

		PersonalRecord personalRecord;

		// Eliminamos el personal record asociado
		personalRecord = curriculum.getPersonalRecord();
		this.personalRecordService.delete(personalRecord);

		// Eliminamos los educations records asociados
		for (final EducationRecord educationRecord : curriculum.getEducationRecords())
			this.educationRecordService.delete(educationRecord);

		// Eliminamos los professional records asociados
		for (final ProfessionalRecord professionalRecord : curriculum.getProfessionalRecords())
			this.professionalRecordService.delete(professionalRecord);

		// Eliminamos los miscellaneous records asociados
		for (final MiscellaneousRecord miscellaneousRecord : curriculum.getMiscellaneousRecords())
			this.miscellaneousRecordService.delete(miscellaneousRecord);

		// Eliminamos los endorser records asociados
		for (final EndorserRecord endorserRecord : curriculum.getEndorserRecords())
			this.endorserRecordService.delete(endorserRecord);

		this.curriculumRepository.delete(curriculum);

	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(!(this.curriculumRepository.exists(curriculum.getId())));

		Curriculum result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();
		result = this.curriculumRepository.save(curriculum);
		handyWorker.setCurriculum(result);

		return result;
	}

	// Other business methods --------------------------

	public Curriculum findCurriculumByTicker(final String ticker) {
		Curriculum result;

		result = this.curriculumRepository.findCurriculumByTicker(ticker);

		return result;
	}

	protected void addEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		Collection<EducationRecord> aux;

		aux = new HashSet<>(curriculum.getEducationRecords());
		aux.add(educationRecord);
		curriculum.setEducationRecords(aux);
	}

	protected void removeEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		Collection<EducationRecord> aux;

		aux = new HashSet<>(curriculum.getEducationRecords());
		aux.remove(educationRecord);
		curriculum.setEducationRecords(aux);
	}

	protected void addEndorserRecord(final Curriculum curriculum, final EndorserRecord endorserRecord) {
		Collection<EndorserRecord> aux;

		aux = new HashSet<>(curriculum.getEndorserRecords());
		aux.add(endorserRecord);
		curriculum.setEndorserRecords(aux);
	}

	protected void removeEndorserRecord(final Curriculum curriculum, final EndorserRecord endorserRecord) {
		Collection<EndorserRecord> aux;

		aux = new HashSet<>(curriculum.getEndorserRecords());
		aux.remove(endorserRecord);
		curriculum.setEndorserRecords(aux);
	}

	protected void addMiscellaneousRecord(final Curriculum curriculum, final MiscellaneousRecord miscellaneousRecord) {
		Collection<MiscellaneousRecord> aux;

		aux = new HashSet<>(curriculum.getMiscellaneousRecords());
		aux.add(miscellaneousRecord);
		curriculum.setMiscellaneousRecords(aux);
	}

	protected void removeMiscellaneousRecord(final Curriculum curriculum, final MiscellaneousRecord miscellaneousRecord) {
		Collection<MiscellaneousRecord> aux;

		aux = new HashSet<>(curriculum.getMiscellaneousRecords());
		aux.remove(miscellaneousRecord);
		curriculum.setMiscellaneousRecords(aux);
	}

	protected void addPersonalRecord(final Curriculum curriculum, final PersonalRecord personalRecord) {
		curriculum.setPersonalRecord(personalRecord);
	}

	protected void removePersonalRecord(final Curriculum curriculum, final PersonalRecord personalRecord) {
		Assert.isNull(personalRecord);
		curriculum.setPersonalRecord(personalRecord);
	}

	protected void addProfessionalRecord(final Curriculum curriculum, final ProfessionalRecord professionalRecord) {
		Collection<ProfessionalRecord> aux;

		aux = new HashSet<>(curriculum.getProfessionalRecords());
		aux.add(professionalRecord);
		curriculum.setProfessionalRecords(aux);
	}

	protected void removeProfessionalRecord(final Curriculum curriculum, final ProfessionalRecord professionalRecord) {
		Collection<ProfessionalRecord> aux;

		aux = new HashSet<>(curriculum.getProfessionalRecords());
		aux.remove(professionalRecord);
		curriculum.setProfessionalRecords(aux);
	}

}
