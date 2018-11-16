
package services;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import repositories.CategoryTranslationRepository;

@Service
@Transactional
public class CategoryTranslationService {

	// Managed repository ------------------------------
	private CategoryTranslationRepository	categoryTranslationRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public CategoryTranslationService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	// Other business methods --------------------------

}
