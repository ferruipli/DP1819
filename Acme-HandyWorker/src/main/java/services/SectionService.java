
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SectionRepository;

@Service
@Transactional
public class SectionService {

	// Managed repository ---------------------------------------------
	@Autowired
	private SectionRepository	sectionRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private SectionService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	//Other business methods-------------------------------------------
}
