
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService {

	// Managed repository ------------------------------
	@Autowired
	private CategoryRepository	categoryRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	// Other business methods --------------------------

}
