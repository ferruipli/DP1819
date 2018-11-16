
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SponsorRepository;

@Service
@Transactional
public class SponsorService {

	// Managed repository ---------------------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private SponsorService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	//Other business methods-------------------------------------------

}
