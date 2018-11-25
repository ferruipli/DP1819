
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Finder;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository ---------------------------------------------
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting services -------------------------------------------
	@Autowired
	private BoxService				boxService;
	@Autowired
	private FinderService			finderService;


	//Constructor ----------------------------------------------------
	public HandyWorkerService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public HandyWorker create() {
		HandyWorker result;
		Finder finder;
		Collection<Application> applications;
		UserAccount userAccount;

		result = new HandyWorker();
		finder = this.finderService.create();
		finder = this.finderService.save(finder);
		applications = new ArrayList<Application>();
		userAccount = new UserAccount();

		Assert.notNull(finder);
		Assert.notNull(applications);
		Assert.notNull(userAccount);

		result.setApplications(applications);
		result.setFinder(finder);
		result.setUserAccount(userAccount);

		Assert.notNull(result.getApplications());
		Assert.notNull(result.getFinder());
		Assert.notNull(result.getUserAccount());

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		final Md5PasswordEncoder encoder;
		final String passwordHash;
		HandyWorker result;
		String make;
		UserAccount userAccount;

		encoder = new Md5PasswordEncoder();
		passwordHash = encoder.encodePassword(handyWorker.getUserAccount().getPassword(), null);
		handyWorker.getUserAccount().setPassword(passwordHash);

		//Para añadirle el make por defecto, eso es solo en caso que acabe de crear
		if (handyWorker.getId() == 0) {

			if (handyWorker.getMiddleName() == null) //si el middle name es nulo que lo cambie a vacio para que el make no sea name+null
				handyWorker.setMiddleName("");
			make = handyWorker.getName() + " " + handyWorker.getMiddleName();
			handyWorker.setMake(make);
			result = this.handyWorkerRepository.save(handyWorker);
			this.boxService.createDefaultBox(handyWorker);
		} else {
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			Assert.isTrue(userAccount.equals(handyWorker.getUserAccount()));
			result = this.handyWorkerRepository.save(handyWorker);

		}

		return result;

	}
	public HandyWorker findOne(final int idHandyWorker) {
		HandyWorker result;

		Assert.isTrue(idHandyWorker != 0);

		result = this.handyWorkerRepository.findOne(idHandyWorker);

		return result;
	}
	public Collection<HandyWorker> findAll() {
		Collection<HandyWorker> result;

		result = this.handyWorkerRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	//Other business methods-------------------------------------------

	public HandyWorker findByPrincipal() {
		HandyWorker result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	protected HandyWorker findByUserAccount(final int userAccountId) {
		HandyWorker result;

		result = this.handyWorkerRepository.findByUserAccount(userAccountId);

		return result;
	}

	public Collection<HandyWorker> findEndorsableHandyWorkers(final int customerId) {
		Collection<HandyWorker> handyWorkers;
		handyWorkers = this.handyWorkerRepository.findEndorsableHandyWorkers(customerId);
		return handyWorkers;
	}
	public int findPhaseCreator(final Phase phase) {
		int result;

		result = this.handyWorkerRepository.findPhaseCreatorId(phase);

		return result;
	}

	public HandyWorker findByReportId(final int reportId) {
		HandyWorker result;

		result = this.handyWorkerRepository.findByReportId(reportId);
		Assert.notNull(result);

		return result;
	}

}
