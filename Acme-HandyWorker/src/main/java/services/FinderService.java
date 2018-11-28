
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
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
	public Collection<FixUpTask> search(final Finder finder) {
		final int maxFinderResults;
		final int timeCacheFinderResults;
		final Pageable pageable;
		final String keyWord;
		final Double startPrice;
		final Double endPrice;
		final Date startDate;
		final Date endDate;
		final String warranty;
		final String category;
		final Page<FixUpTask> pageFixUpTasks;
		final Collection<FixUpTask> collectionFixUpTask;

		timeCacheFinderResults = this.customisationService.find().getTimeCachedFinderResults();

		if (this.compareTime(finder.getLastUpdate(), timeCacheFinderResults)) {

			maxFinderResults = this.customisationService.find().getMaxFinderResults();
			pageable = new PageRequest(0, maxFinderResults);
			keyWord = this.checkKeyWord(finder);
			startPrice = this.checkStartPrice(finder);
			endPrice = this.checkEndPrice(finder);
			startDate = this.checkStartDate(finder);
			endDate = this.checkEndDate(finder);
			warranty = this.checkWarranty(finder);
			category = this.checkCategory(finder);

			System.out.println(keyWord);
			System.out.println(startPrice);
			System.out.println(endPrice);
			System.out.println(endDate);
			System.out.println(startDate);
			System.out.println(warranty);
			System.out.println(category);

			pageFixUpTasks = this.finderRepository.findFixUpTaskFinder(keyWord, startPrice, endPrice, startDate, endDate, warranty, category, pageable);

			collectionFixUpTask = pageFixUpTasks.getContent();
			finder.setLastUpdate(LocalDate.now().toDate());
		} else
			collectionFixUpTask = finder.getFixUpTasks();
		return collectionFixUpTask;
	}
	private String checkKeyWord(final Finder finder) {
		String result;
		result = finder.getKeyword();
		if (result == null)
			finder.setKeyword("");
		return finder.getKeyword();
	}
	private Double checkStartPrice(final Finder finder) {
		Double result;
		result = finder.getStartPrice();
		if (result == null)
			finder.setStartPrice(0.0);
		return finder.getStartPrice();
	}
	private Double checkEndPrice(final Finder finder) {
		Double result;
		result = finder.getEndPrice();
		if (result == null)
			finder.setEndPrice(1000000000.0);
		return finder.getEndPrice();
	}
	private Date checkStartDate(final Finder finder) {
		if (finder.getStartDate() == null) {
			finder.setStartDate(LocalDate.parse("0000-01-01").toDate());
			return finder.getStartDate();
		} else {
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
			final String stringStartDate = dateFormat.format(finder.getStartDate());

			Date dateStartDate = LocalDate.now().toDate();
			try {
				dateStartDate = dateFormat.parse(stringStartDate);
			} catch (final ParseException e) {
				e.printStackTrace();
			}
			return dateStartDate;
		}
	}

	private Date checkEndDate(final Finder finder) {
		if (finder.getEndDate() == null) {
			finder.setEndDate(LocalDate.parse("9999-01-01").toDate());
			return finder.getEndDate();
		} else {
			final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z Z");
			final String stringEndDate = dateFormat.format(finder.getEndDate());

			Date dateEndDate = LocalDate.now().toDate();
			try {
				dateEndDate = dateFormat.parse(stringEndDate);
			} catch (final ParseException e) {
				e.printStackTrace();
			}
			return dateEndDate;
		}
	}
	private String checkWarranty(final Finder finder) {
		String result;
		result = finder.getWarranty();
		if (result == null)
			finder.setWarranty("");
		return finder.getWarranty();
	}
	private String checkCategory(final Finder finder) {
		String result;
		result = finder.getCategory();
		if (result == null)
			finder.setCategory("");
		return finder.getCategory();
	}

	private boolean compareTime(final Date lastUpdate, final Integer cache) {
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
