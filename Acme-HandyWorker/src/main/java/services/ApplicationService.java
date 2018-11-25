
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

		Application applicationBD;
		Application result;

		applicationBD = this.findOne(application.getId());

		//si estoy guardando despues de crear
		if (application.getId() == 0)
			result = this.applicationRepository.save(application);
		//this.messageService.messageForNotificationToStatusPending(application);
		//si no ha cambiado el estado, simplemente se ha modificado
		else if (applicationBD.getStatus().equals(application.getStatus()))
			result = this.applicationRepository.save(application);
		//si se ha actualizado el estado
		else
			//TODO	
			//			
			//			 if (application.getStatus().equals("REJECTED"))
			//				this.messageService.messageForNotificationToStatusRejected(application);
			//			else if (application.getStatus().equals("ACCEPTED"))
			//				this.messageService.messageForNotificationToStatusAccepted(application);
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

	public void changeStatus(final Application application) {

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
}
