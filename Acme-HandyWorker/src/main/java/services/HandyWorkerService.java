
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

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository ---------------------------------------------
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private HandyWorkerService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public HandyWorker create() {
		HandyWorker result;
		Finder finder;
		Collection<Application> applications;
		UserAccount userAccount;

		result = new HandyWorker();
		finder = new Finder();
		applications = new ArrayList<Application>();
		userAccount = new UserAccount();

		result.setApplications(applications);
		result.setFinder(finder);
		result.setUserAccount(userAccount);

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		final Md5PasswordEncoder encoder;
		final String passwordHash;
		HandyWorker result;

		encoder = new Md5PasswordEncoder();
		passwordHash = encoder.encodePassword(handyWorker.getUserAccount().getPassword(), null);
		handyWorker.getUserAccount().setPassword(passwordHash);
		result = this.handyWorkerRepository.save(handyWorker);

		result = this.handyWorkerRepository.save(handyWorker);

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
	public void delete(final HandyWorker handyWorker) {
		Assert.isTrue(handyWorker.getId() != 0);
		Assert.notNull(handyWorker);
		Assert.isTrue(this.handyWorkerRepository.exists(handyWorker.getId()));

		this.handyWorkerRepository.delete(handyWorker);
	}
	//Other business methods-------------------------------------------

	public HandyWorker findByPrincipal() {
		HandyWorker result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		result = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

}
