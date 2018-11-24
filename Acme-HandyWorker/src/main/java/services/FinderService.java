
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
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	// Managed repository ---------------------------------------------
	@Autowired
	private FinderRepository		finderRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;
	@Autowired
	private CustomisationService	customisationService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


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
		fixUpTasks = this.fixUpTaskService.findAll();

		result.setFixUpTasks(fixUpTasks);
		result.setLastUpdate(date);

		return result;
	}
	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		HandyWorker handyWorker;
		Finder result;
		Date date;

		date = new Date();

		if (finder.getId() != 0) {

			handyWorker = this.handyWorkerService.findByPrincipal();
			System.out.println(handyWorker);
			Assert.isTrue(handyWorker.getFinder().getId() == finder.getId());
		}

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

}
