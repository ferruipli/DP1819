
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create() {
		Application result;

		result = new Application();

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;

		result = this.applicationRepository.save(application);

		return result;
	}

	public Application findOne(final int idApplication) {
		Application result;

		result = this.applicationRepository.findOne(idApplication);

		return result;
	}
	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Application application) {
		Assert.isTrue(application.getId() != 0);
		Assert.notNull(application);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));

		this.applicationRepository.delete(application);
	}
	//Other business methods-------------------------------------------

}
