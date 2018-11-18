
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
import domain.MiscellaneousRecord;
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


	// Constructors ------------------------------------
	public CurriculumService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Curriculum create() {
		Curriculum result;

		result = new Curriculum();

		result.setTicker(this.utilityService.generateValidTicker());
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

		this.curriculumRepository.delete(curriculum);

	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(!(this.curriculumRepository.exists(curriculum.getId())));

		Curriculum result;

		result = this.curriculumRepository.save(curriculum);

		return result;
	}

	// Other business methods --------------------------

	public Collection<String> findAllTickers() {
		final Collection<String> results;

		results = this.curriculumRepository.findAllTickers();

		return results;
	}

	protected void removeEducationRecord(final Curriculum curriculum, final EducationRecord educationRecord) {
		Collection<EducationRecord> aux;

		aux = new HashSet<>(curriculum.getEducationRecords());
		aux.remove(educationRecord);
		curriculum.setEducationRecords(aux);
	}

}
