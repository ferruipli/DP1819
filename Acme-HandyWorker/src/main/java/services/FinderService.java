
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
	//	public Collection<FixUpTask> search(final Finder finder) {
	//		String keyWord;
	//		Double startPrice;
	//		Double endPrice;
	//		Date startDate;
	//		Date endDate;
	//		String warranty;
	//		String category;
	//		Page<FixUpTask> collectionFixUpTasks;
	//
	//		final int maxFinderResults;
	//		final int timeCacheFinderResults;
	//		maxFinderResults = 10;
	//		final Pageable pageable = new PageRequest(0, maxFinderResults);
	//
	//		keyWord = finder.getKeyword();
	//		startPrice = finder.getStartPrice();
	//		endPrice = finder.getEndPrice();
	//		startDate = finder.getStartDate();
	//		endDate = finder.getEndDate();
	//		warranty = finder.getWarranty();
	//		category = finder.getCategory();
	//
	//		collectionFixUpTasks = this.finderRepository.findFixUpTaskFinder(keyWord, startPrice, endPrice, startDate, endDate, warranty, category, pageable);
	//		return collectionFixUpTasks.getContent();
	//	}

}
