
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

	@Autowired
	private UtilityService			utilityService;


	//Constructor ----------------------------------------------------
	public FinderService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Finder create() {
		Finder result;

		result = new Finder();
		result.setFixUpTasks(this.fixUpTaskService.findAll());

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		this.utilityService.checkActorIsBanned(this.handyWorkerService.findByPrincipal());
		Finder result;
		Date date;
		Collection<FixUpTask> fixUpTasks;

		date = this.utilityService.current_moment();

		if (finder.getId() != 0)
			this.checkByPrincipal(finder);

		finder.setLastUpdate(date);
		fixUpTasks = this.search(finder);
		finder.setFixUpTasks(fixUpTasks);
		result = this.finderRepository.save(finder);

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		Assert.isTrue(finderId != 0);

		result = this.finderRepository.findOne(finderId);

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
		this.checkByPrincipal(finder);
		this.utilityService.checkActorIsBanned(this.handyWorkerService.findByPrincipal());

		final Pageable pageable;
		final int maxFinderResults;
		final String keyWord, warranty, category;
		final Double startPrice, endPrice;
		final Date startDate, endDate;
		final Page<FixUpTask> pageFixUpTasks;
		final Collection<FixUpTask> collectionFixUpTask;

		maxFinderResults = this.customisationService.find().getMaxFinderResults();
		pageable = new PageRequest(0, maxFinderResults);
		keyWord = this.checkKeyWord(finder);
		startPrice = this.checkStartPrice(finder);
		endPrice = this.checkEndPrice(finder);
		startDate = this.checkStartDate(finder);
		endDate = this.checkEndDate(finder);
		warranty = this.checkWarranty(finder);
		category = this.checkCategory(finder);

		pageFixUpTasks = this.fixUpTaskService.findFixUpTaskFinder(keyWord, startPrice, endPrice, startDate, endDate, warranty, category, pageable);

		collectionFixUpTask = pageFixUpTasks.getContent();
		finder.setLastUpdate(LocalDate.now().toDate());

		return collectionFixUpTask;
	}

	protected void checkByPrincipal(final Finder finder) {
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(handyWorker.getFinder().equals(finder));
	}

	// private methods -----------------------------------------------
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
			finder.setStartPrice(0.00);

		return finder.getStartPrice();
	}

	private Double checkEndPrice(final Finder finder) {
		Double result;
		result = finder.getEndPrice();

		if (result == null)
			finder.setEndPrice(1000000.00);

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

	public boolean compareTime(final Date lastUpdate, final Integer cache) {
		final Boolean result;
		Long time, hours;
		Date date;

		date = new Date();

		//time is milliseconds
		//*1000 to seconds
		//*60 to minutes
		time = date.getTime() - lastUpdate.getTime();
		hours = time / (1000 * 60 * 60);

		if (hours > cache)
			result = true;
		else
			result = false;

		return result;
	}

}
