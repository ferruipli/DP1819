
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
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
	private MessageService			messageService;

	@Autowired
	private UtilityService			utilityService;


	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create(final FixUpTask fixUpTask) {
		Application result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		result = new Application();
		result.setStatus("PENDING");
		result.setHandyWorker(handyWorker);
		result.setFixUpTask(fixUpTask);

		return result;
	}

	// Accedo a la bd y cojo el application que esta guardado para ver cual era su
	// estado anterior en caso que haya cambiado de pending a rejected pido
	// credit card, ademas si ha cambiado envio mensaje
	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("PENDING"));

		Application result;
		Date moment;

		if (application.getId() == 0) {
			moment = this.utilityService.current_moment();
			application.setRegisterMoment(moment);
		} else
			this.checkByPrincipal(application);

		result = this.applicationRepository.save(application);

		return result;
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

	public void delete(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		this.checkByPrincipal(application);

		this.removeApplicationToHandyWorker(application);
		this.removeApplicationToFixUpTask(application);

		this.applicationRepository.delete(application);
	}

	//Other business methods-------------------------------------------
	protected void checkByPrincipal(final Application application) {
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		Assert.isTrue(handyWorker.equals(application.getHandyWorker()));
	}

	protected Collection<Application> findApplicationByCreditCard(final int id) {
		Collection<Application> sponsorships;

		sponsorships = this.applicationRepository.findApplicationByCreditCard(id);

		return sponsorships;
	}

	// Accedo a la bd y cojo el application que esta guardado para ver cual era su
	// estado anterior en caso que haya cambiado de pending a accepted pido credit card,
	// ademas si ha cambiado envio mensaje
	public Application changeStatus(final Application application) {
		Assert.notNull(application);

		Application applicationBd, result;
		FixUpTask fixUpTask;
		final Collection<Application> applications;

		applicationBd = this.findOne(application.getId());
		// Me aseguro de que ha cambiado el estado
		Assert.isTrue(!(applicationBd.getStatus().equals(application.getStatus())));

		this.messageService.messageToStatus(application, application.getStatus());

		result = this.applicationRepository.save(application);

		if (application.getStatus().equals("ACCEPTED")) {
			fixUpTask = application.getFixUpTask();
			applications = fixUpTask.getApplications();
			for (final Application a : applications) {
				a.setStatus("REJECTED");
				this.changeStatus(a);
			}
		}

		return result;
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
}
