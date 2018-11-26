
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	//Other business methods-------------------------------------------
	//TODO
	public Collection<FixUpTask> search(final Finder finder) {
		String keyWord;
		Double startPrice;
		Double endPrice;
		Date startDate;
		Date endDate;
		String warranty;
		String category;
		Page<FixUpTask> collectionFixUpTasks;

		final int maxFinderResults;
		final int timeCacheFinderResults;
		maxFinderResults = 10;
		final Pageable pageable = new PageRequest(0, maxFinderResults);

		keyWord = finder.getKeyword();
		startPrice = finder.getStartPrice();
		endPrice = finder.getEndPrice();
		startDate = finder.getStartDate();
		endDate = finder.getEndDate();
		warranty = finder.getWarranty();
		category = finder.getCategory();

		collectionFixUpTasks = this.finderRepository.findFixUpTaskFinder(keyWord, startPrice, endPrice, startDate, endDate, warranty, category, pageable);
		return collectionFixUpTasks.getContent();
	}

	//	public Collection<FixUpTask> search(final Finder finder) {
	//		Collection<FixUpTask> tasksresult;
	//		final Page<FixUpTask> collectionFixUpTasks;
	//		final int maxFinderResults;
	//		final int timeCacheFinderResults;
	//
	//		//TODO
	//		//	maxFinderResults = customisationService.getmaxFinderResults();
	//		//timeCacheFinderResults = customisationService.gettimeFinderREsults();
	//		//este valor de maxFInder result es temporal
	//		tasksresult = new ArrayList<FixUpTask>();
	//		maxFinderResults = 10;
	//		timeCacheFinderResults = 1;
	//		final Pageable pageable = new PageRequest(0, maxFinderResults);
	//		//si el tiempo de la cache ya ha pasado
	//		if (this.compareTime(finder.getLastUpdate(), timeCacheFinderResults)) {
	//
	//			collectionFixUpTasks = this.finderRepository.findFixUpTaskFinder(, finder.getStartPrice(), finder.getEndPrice(), finder.getStartDate(), finder.getEndDate(), finder.getWarranty(), finder.getCategory(), pageable);
	//			System.out.println(finder.getKeyword() + finder.getStartPrice() + finder.getEndPrice() + finder.getStartDate() + finder.getEndDate() + finder.getWarranty() + finder.getCategory());
	//			tasksresult = collectionFixUpTasks.getContent();
	//
	//		}
	//		return tasksresult;
	//	}
	//TODO: HACER PRIVADO
	//	public boolean compareTime(final Date lastUpdate, final Integer cache) {
	//		final Boolean result;
	//		Long time;
	//		Long hours;
	//		Date date;
	//
	//		date = new Date();
	//
	//		//time me lo da en milesegundos
	//		//*1000 paso a seg
	//		//*60 a minutos
	//		time = date.getTime() - lastUpdate.getTime();
	//		hours = time / (1000 * 60 * 60);
	//
	//		if (hours > cache)
	//			result = true;
	//		else
	//			result = false;
	//		return result;
	//
	//	}
	//
	//	public Collection<FixUpTask> search(final Finder finder) {
	//		Collection<FixUpTask> fixUpTasks;
	//		Collection<FixUpTask> tasks;
	//		final Page<FixUpTask> collectionFixUpTasks;
	//		final int maxFinderResults;
	//		final int timeCacheFinderResults;
	//		final String keyWord;
	//		final Double startPrice;
	//		final Double endPrice;
	//		final Date startDate;
	//		final Date endDate;
	//		final String category;
	//		final String warranty;
	//
	//		fixUpTasks = this.fixUpTaskService.findAll();
	//		tasks = new ArrayList<FixUpTask>();
	//
	//		//TODO
	//		//	maxFinderResults = customisationService.getmaxFinderResults();
	//		//timeCacheFinderResults = customisationService.gettimeFinderREsults();
	//		//este valor de maxFInder result es temporal
	//		maxFinderResults = 10;
	//		timeCacheFinderResults = 1;
	//		final Pageable pageable = new PageRequest(0, maxFinderResults);
	//		//si el tiempo de la cache ya ha pasado
	//		if (this.compareTime(finder.getLastUpdate(), timeCacheFinderResults)) {
	//			keyWord = finder.getKeyword();
	//			startPrice = finder.getStartPrice();
	//			endPrice = finder.getEndPrice();
	//			startDate = finder.getStartDate();
	//			endDate = finder.getEndDate();
	//			category = finder.getCategory();
	//			warranty = finder.getWarranty();
	//
	//		}
	//
	//		return tasks;
	//	}

}
