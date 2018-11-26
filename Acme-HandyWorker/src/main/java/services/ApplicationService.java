
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
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
	private MessageService			messageService;


	//Constructor ----------------------------------------------------
	public ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Application create(final FixUpTask fixUpTask) {
		Application result;
		Date date;
		String status;
		HandyWorker handyWorker;

		result = new Application();
		date = new Date();
		status = "PENDING";
		handyWorker = this.handyWorkerService.findByPrincipal();

		result.setRegisterMoment(date);
		result.setStatus(status);
		result.setHandyWorker(handyWorker);
		result.setFixUpTask(fixUpTask);

		return result;
	}

	public Application save(final Application application) {
		//Accedo a la bd y cojo el application que esta guardado para ver cual era su estado anterior
		//en caso que haya cambiado de pending a rejected pido credit card, ademas si ha cambiado envio mensaje
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Application result;

		result = this.applicationRepository.save(application);

		return result;
	}
	public Application findOne(final int idApplication) {
		Application result;

		result = this.applicationRepository.findOne(idApplication);

		return result;
	}
	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Application application) {
		HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.findByPrincipal();
		Assert.isTrue(handyWorker.equals(application.getHandyWorker()));
		Assert.isTrue(application.getId() != 0);
		Assert.notNull(application);
		Assert.isTrue(this.applicationRepository.exists(application.getId()));

		this.removeApplicationToHandyWorker(application);
		this.removeApplicationToFixUpTask(application);

		this.applicationRepository.delete(application);
	}
	//Other business methods-------------------------------------------

	public Application changeStatus(final Application application) {
		//Accedo a la bd y cojo el application que esta guardado para ver cual era su estado anterior
		//en caso que haya cambiado de pending a rejected pido credit card, ademas si ha cambiado envio mensaje
		Assert.notNull(application);
		Application applicationBd;
		Application result;
		FixUpTask fixUpTask;
		final Collection<Application> applications;
		applicationBd = this.findOne(application.getId());
		//aseguro que ha cambiado el estado
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
		Assert.isTrue(handyWorker.getApplications().contains(application));
		handyWorker.getApplications().remove(application);
		Assert.isTrue(!(handyWorker.getApplications().contains(application)));
	}
	public void removeApplicationToFixUpTask(final Application application) {
		FixUpTask fixUpTask;
		fixUpTask = application.getFixUpTask();
		Assert.isTrue(fixUpTask.getApplications().contains(application));
		fixUpTask.getApplications().remove(application);
		Assert.isTrue(!(fixUpTask.getApplications().contains(application)));
	}
	public void enterCreditCard(final Application application, final CreditCard creditCard) {
		application.setCreditCard(creditCard);
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
	}//Req 12.5.6
	public Double findRatioAcceptedApplications() {
		Double result;

		result = this.applicationRepository.findRatioAcceptedApplications();

		return result;
	}//Req 12.5.7
	public Double findRatioRejectedApplications() {
		Double result;

		result = this.applicationRepository.findRatioRejectedApplications();

		return result;
	}//Req 12.5.8
	public Double findRatioPendingApplicationsNotChangeStatus() {
		Double result;

		result = this.applicationRepository.findRatioPendingApplicationsNotChangeStatus();

		return result;
	}
}
