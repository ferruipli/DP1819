
package services;

import java.util.ArrayList;
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
		Collection<FixUpTask> fixUpTasks;
		Collection<FixUpTask> tasks;
		Page<FixUpTask> collectionFixUpTasks;
		final int maxFinderResults;
		final int timeCacheFinderResults;
		final String keyWord;
		final Double startPrice;
		final Double endPrice;
		final Date startDate;
		final Date endDate;
		final String category;
		final String warranty;

		fixUpTasks = finder.getFixUpTasks();
		tasks = new ArrayList<FixUpTask>();

		//TODO
		//	maxFinderResults = customisationService.getmaxFinderResults();
		//timeCacheFinderResults = customisationService.gettimeFinderREsults();
		//este valor de maxFInder result es temporal
		maxFinderResults = 10;
		timeCacheFinderResults = 1;
		final Pageable pageable = new PageRequest(0, maxFinderResults);
		//si el tiempo de la cache ya ha pasado
		if (this.compareTime(finder.getLastUpdate(), timeCacheFinderResults)) {
			keyWord = finder.getKeyword();
			startPrice = finder.getStartPrice();
			endPrice = finder.getEndPrice();
			startDate = finder.getStartDate();
			endDate = finder.getEndDate();
			category = finder.getCategory();
			warranty = finder.getWarranty();

			if (keyWord != null) {
				collectionFixUpTasks = this.finderRepository.findFixUpTaskByKeyword(keyWord, pageable);
				tasks = collectionFixUpTasks.getContent();

			}

			if (startPrice != null) {
				final Page<FixUpTask> pagestartPrice;
				pagestartPrice = this.finderRepository.findFixUpTaskByStartPrice(startPrice, pageable);
				if (pagestartPrice.getNumber() == 0)
					tasks = new ArrayList<FixUpTask>();
				else
					tasks.retainAll(pagestartPrice.getContent());

			}

			if (endPrice != null) {
				final Page<FixUpTask> pagesendPrice;
				pagesendPrice = this.finderRepository.findFixUpTaskByEndPrice(endPrice, pageable);
				if (pagesendPrice.getNumber() == 0)
					tasks = new ArrayList<FixUpTask>();
				else
					tasks.retainAll(pagesendPrice.getContent());

			}

			if (startDate != null) {
				Page<FixUpTask> pagestartDate;
				pagestartDate = this.finderRepository.findFixUpTaskByStartDate(startDate, pageable);
				if (pagestartDate.getNumber() == 0)
					tasks = new ArrayList<FixUpTask>();
				else
					tasks.retainAll(pagestartDate.getContent());
			}

			if (endDate != null) {
				Page<FixUpTask> pagesendDate;
				pagesendDate = this.finderRepository.findFixUpTaskByEndDate(endDate, pageable);
				if (pagesendDate.getNumber() == 0)
					tasks = new ArrayList<FixUpTask>();
				else
					tasks.retainAll(pagesendDate.getContent());
			}
			//TODO
			//			if (category != null) {
			//				Page<FixUpTask> pagescategory;
			//				pagescategory = this.finderRepository.findFixUpTaskByCategory(category, pageable);
			//				if (pagescategory.getNumber() == 0)
			//					tasks = new ArrayList<FixUpTask>();
			//				else
			//					tasks.retainAll(pagescategory.getContent());
			//			}
			if (warranty != null) {
				Page<FixUpTask> pageswarranty;
				pageswarranty = this.finderRepository.findFixUpTaskByWarranty(warranty, pageable);
				if (pageswarranty.getNumber() == 0)
					tasks = new ArrayList<FixUpTask>();
				else
					tasks.retainAll(pageswarranty.getContent());
			}

		} else
			return fixUpTasks;
		return tasks;
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
