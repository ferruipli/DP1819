
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryTranslationRepository;
import domain.CategoryTranslation;

@Service
@Transactional
public class CategoryTranslationService {

	final String[]							LANGUAGES	= {
		"Espa�ol", "Ingles"
														};

	// Managed repository ------------------------------
	@Autowired
	private CategoryTranslationRepository	categoryTranslationRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public CategoryTranslationService() {
		super();
	}

	// Simple CRUD methods -----------------------------
	public CategoryTranslation create() {
		CategoryTranslation result;

		result = new CategoryTranslation();

		return result;
	}

	public CategoryTranslation save(final CategoryTranslation categoryTranslation) {
		Assert.notNull(categoryTranslation);
		Assert.isTrue(this.validLanguage(categoryTranslation));

		CategoryTranslation result;

		result = this.categoryTranslationRepository.save(categoryTranslation);

		return result;
	}

	public void delete(final CategoryTranslation categoryTranslation) {
		Assert.notNull(categoryTranslation);
		Assert.isTrue(categoryTranslation.getId() != 0);

		this.categoryTranslationRepository.delete(categoryTranslation);
	}

	// Other business methods --------------------------

	// Private methods ---------------------------------
	private boolean validLanguage(final CategoryTranslation categoryTranslation) {
		List<String> languages;
		int n;

		n = this.LANGUAGES.length;
		languages = new ArrayList<>();

		for (int i = 0; i < n; i++)
			languages.add(this.LANGUAGES[i]);

		return languages.contains(categoryTranslation.getLanguage());
	}

}
