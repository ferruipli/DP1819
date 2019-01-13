
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Curriculum;
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
	private FinderService			finderService;

	@Autowired
	private ActorService			actorService;


	//Constructor ----------------------------------------------------
	public HandyWorkerService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public HandyWorker create() {
		HandyWorker result;
		Finder finder;
		Collection<Application> applications;

		result = new HandyWorker();
		finder = this.finderService.create();
		finder = this.finderService.save(finder);
		applications = new ArrayList<Application>();

		result.setApplications(applications);
		result.setFinder(finder);
		result.setUserAccount(this.actorService.createUserAccount(Authority.HANDYWORKER));

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		final HandyWorker result;
		String make;
		result = (HandyWorker) this.actorService.save(handyWorker);

		//To add default make,it's just in case  it was blank

		if (result.getMake() == "")
			if (result.getMiddleName() == null) {
				result.setMiddleName("");
				make = result.getName() + " " + result.getMiddleName();
				result.setMake(make);
			} else {
				make = result.getName() + " " + result.getMiddleName();
				result.setMake(make);
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
	protected HandyWorker findHandyWorkerBySection(final int id) {
		HandyWorker handyWoker;

		handyWoker = this.handyWorkerRepository.findHandyWorkerBySection(id);

		return handyWoker;
	}

	public HandyWorker findByPrincipal() {
		HandyWorker result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	public HandyWorker findByUserAccount(final int userAccountId) {
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

	protected void addCurriculum(final HandyWorker handyWorker, final Curriculum curriculum) {
		handyWorker.setCurriculum(curriculum);
	}

	protected void removeCurriculum(final HandyWorker handyWorker) {
		handyWorker.setCurriculum(null);
	}
	//Req 12.5.10
	public Collection<HandyWorker> atLeast10Application() {
		final Collection<HandyWorker> result;

		result = this.handyWorkerRepository.atLeast10Application();

		return result;
	}
	//Req 38.5.5
	public Collection<HandyWorker> topThreeHandyWorker() {
		final Collection<HandyWorker> results;
		Page<HandyWorker> handyWorkers;
		Pageable pageable;

		pageable = new PageRequest(0, 3);
		handyWorkers = this.handyWorkerRepository.topThreeCustomer(pageable);

		results = handyWorkers.getContent();

		return results;
	}

}
