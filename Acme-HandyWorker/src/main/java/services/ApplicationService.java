
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationService {

	// Managed repository ---------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private ApplicationService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	//Other business methods-------------------------------------------

}
