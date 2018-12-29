
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private LoginService			loginService;


	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create(final FixUpTask fixUpTask) {
		Application result;
		HandyWorker handyWorker;
		CreditCard creditCard;
		Date moment;

		handyWorker = this.handyWorkerService.findByPrincipal();
		creditCard = this.utilityService.createnewCreditCard();
		moment = this.utilityService.current_moment();

		Assert.notNull(handyWorker.getCurriculum());

		result = new Application();
		result.setStatus("PENDING");
		result.setHandyWorker(handyWorker);
		result.setFixUpTask(fixUpTask);
		result.setCreditCard(creditCard);
		result.setRegisterMoment(moment);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Application result;

		if (application.getId() == 0) {
			Assert.notNull(application.getHandyWorker().getCurriculum());
			this.fixUpTaskService.addApplication(application.getFixUpTask(), application);
		} else {
			if (LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER"))
				Assert.notNull(application.getHandyWorker().getCurriculum());
			if (LoginService.getPrincipal().getAuthorities().contains("CUSTOMER"))
				if (this.utilityService.checkIfCreditCardChanged(application.getCreditCard())) {
					//Check that number of accepted application is 0
					this.checkAcceptedApplication(application);
					application.setStatus("ACCEPTED");
				}
		}
		result = this.applicationRepository.save(application);

		this.fixUpTaskService.addApplication(result.getFixUpTask(), result);

		return result;
	}
	private void checkAcceptedApplication(final Application application) {
		Application app;
		app = this.findAcceptedApplication(application.getFixUpTask().getId());
		Assert.isTrue(app == null);
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	//Other business methods-------------------------------------------
	public void addCreditCard(final Application application, final CreditCard creditCard) {
		Assert.isTrue(this.utilityService.checkCreditCard(creditCard));
		application.setCreditCard(creditCard);
	}

	protected void checkByPrincipal(final Application application) {
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(handyWorker.equals(application.getHandyWorker()));
	}

	public Application changeStatus(final Application application) {
		Assert.notNull(application);

		FixUpTask fixUpTask;
		final Collection<Application> applications;

		this.messageService.messageToStatus(application, application.getStatus());

		if (application.getStatus().equals("ACCEPTED")) {
			fixUpTask = application.getFixUpTask();
			applications = fixUpTask.getApplications();
			applications.remove(application);
			for (final Application a : applications) {
				a.setStatus("REJECTED");
				this.changeStatus(a);
			}
			Assert.isTrue(this.utilityService.checkCreditCard(application.getCreditCard()), "Tarjeta de credito no valida");
		} else
			application.setStatus("REJECTED");

		return application;
	}

	public void removeApplicationToHandyWorker(final Application application) {
		HandyWorker handyWorker;

		handyWorker = application.getHandyWorker();

		handyWorker.getApplications().remove(application);
	}

	public void removeApplicationToFixUpTask(final Application application) {
		FixUpTask fixUpTask;

		fixUpTask = application.getFixUpTask();

		fixUpTask.getApplications().remove(application);
	}

	//Req 12.5.4
	public Double[] findDataOfApplicationPrice() {
		Double[] result;

		result = this.applicationRepository.findDataOfApplicationPrice();

		return result;
	}

	//Req 12.5.5
	public Double findRatioPendingApplications() {
		Double result;

		result = this.applicationRepository.findRatioPendingApplications();

		return result;
	}

	//Req 12.5.6
	public Double findRatioAcceptedApplications() {
		Double result;

		result = this.applicationRepository.findRatioAcceptedApplications();

		return result;
	}

	//Req 12.5.7
	public Double findRatioRejectedApplications() {
		Double result;

		result = this.applicationRepository.findRatioRejectedApplications();

		return result;
	}

	//Req 12.5.8
	public Double findRatioPendingApplicationsNotChangeStatus() {
		Double result;

		result = this.applicationRepository.findRatioPendingApplicationsNotChangeStatus();

		return result;
	}

	protected Application findAcceptedApplication(final int fixUpTaskId) {
		Application result;

		result = this.applicationRepository.findAcceptedApplication(fixUpTaskId);

		return result;
	}
	public Page<Application> findApplicationByHandyWorker(final Pageable pageable) {
		Page<Application> applications;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();
		applications = this.applicationRepository.findApplicationByHandyWorker(handyWorker.getId(), pageable);

		Assert.isTrue((applications.getNumberOfElements() != 0));
		return applications;

	}
	public Page<Application> findApplicationByFixUpTask(final int fixUpTaskId, final Pageable pageable) {
		Page<Application> result;

		result = this.applicationRepository.findApplicationByFixUpTask(fixUpTaskId, pageable);

		return result;
	}

}
