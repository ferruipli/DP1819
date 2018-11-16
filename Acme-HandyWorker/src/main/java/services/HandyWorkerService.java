
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.HandyWorkerRepository;

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

	//Other business methods-------------------------------------------

}
