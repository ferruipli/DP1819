
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;
import repositories.CreditCardRepository;

@Service
@Transactional
public class CreditCardService {

	// Managed repository ---------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private CreditCardService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	//Other business methods-------------------------------------------

}
