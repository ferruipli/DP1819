
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FinderService {

	// Managed repository ---------------------------------------------
	@Autowired
	private FinderRepository		finderRepository;

	// Supporting services -------------------------------------------

	private FixUpTaskService		fixUpTaskService;
	private CustomisationService	customisationService;


	//Constructor ----------------------------------------------------
	public FinderService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Finder create() {
		Finder result;
		final Collection<FixUpTask> fixUpTasks;
		Date date;

		result = new Finder();
		date = new Date();
		//TODO
		//		fixUpTasks = fixUpTaskService.findAll();

		//		result.setFixUpTasks(fixUpTasks);
		result.setLastUpdate(date);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		Finder result;
		Date date;

		date = new Date();

		finder.setLastUpdate(date);
		result = this.finderRepository.save(finder);

		return result;
	}

	public Finder findOne(final int idFinder) {
		Finder result;

		Assert.isTrue(idFinder != 0);

		result = this.finderRepository.findOne(idFinder);

		return result;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Finder finder) {
		Assert.isTrue(finder.getId() != 0);
		Assert.notNull(finder);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	//Other business methods-------------------------------------------

	public Collection<FixUpTask> search(final Finder finder) {
		final Collection<FixUpTask> fixUpTasks;
		final int maxFinderResults;
		fixUpTasks = finder.getFixUpTasks();

		//TODO
		//	maxFinderResults = customisationService.getmaxFinderResults();
		//este valor de maxFInder result es temporal
		maxFinderResults = 1;
		if (this.compareTime(finder.getLastUpdate(), maxFinderResults)) {

		}

		return fixUpTasks;

	}
	//TODO: HACER PRIVADO
	public boolean compareTime(final Date lastUpdate, final Integer cache) {
		final Boolean result;
		Long time;
		Long hours;
		Date date;

		date = new Date();

		//time me lo da en milesegundos
		//*1000 paso a seg
		//*60 a minutos
		time = date.getTime() - lastUpdate.getTime();
		hours = time / (1000 * 60 * 60);

		if (hours > cache)
			result = true;
		else
			result = false;
		return result;

	}
}
