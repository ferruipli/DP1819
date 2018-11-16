
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
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

		result = new HandyWorker();

		return result;
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);

		HandyWorker result;

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

}
