
package services;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.CategoryTranslation;

@Service
@Transactional
public class CategoryService {

	final int							IDIOMAS_SOPORTADOS	= 2;

	// Managed repository ------------------------------
	@Autowired
	private CategoryRepository			categoryRepository;

	// Supporting services -----------------------------
	@Autowired
	private CategoryTranslationService	categoryTranslationService;


	// Constructors ------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD methods -----------------------------
	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);

		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> results;

		results = this.categoryRepository.findAll();

		return results;
	}

	public Category create() {
		Category result;

		result = new Category();

		result.setCategoriesTranslations(Collections.<CategoryTranslation> emptySet());
		result.setDescendants(Collections.<Category> emptySet());

		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		Assert.notNull(category.getParent());
		Assert.isTrue(category.getCategoriesTranslations().size() == this.IDIOMAS_SOPORTADOS);
		Assert.isTrue(this.validLanguages(category));

		final Category result, parent_category, old_category, old_parent_category;

		result = this.categoryRepository.save(category);

		parent_category = this.findOneToEdit(result.getParent().getId());

		if (category.getId() == 0)
			this.addDescendantCategory(parent_category, result);
		else {
			// Si category cambia de padre, entonces hay que realizar cambios en la jerarquia
			old_category = this.findOne(category.getId());
			if (!old_category.getParent().equals(parent_category)) {
				// El antiguo padre de category deja de tener como descendiente a category
				old_parent_category = this.findOneToEdit(old_category.getParent().getId());
				this.removeDescendantCategory(old_parent_category, result);

				// El nuevo padre de category pasa a tener a category como descendiente
				this.addDescendantCategory(parent_category, result);
			}
		}

		return result;
	}
	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		final Category parent_category = this.findOneToEdit(category.getParent().getId());
		final Collection<Category> descendant_categories = category.getDescendants();
		Category cat = null;

		// Actualizar atributos del padre de category
		this.removeDescendantCategory(parent_category, category);
		if (!descendant_categories.isEmpty()) {
			this.addDescendantCategories(parent_category, descendant_categories);

			// Actualizar atributos de los descendientes de category
			for (final Category c : descendant_categories) {
				cat = this.findOneToEdit(c.getId());
				this.updateParent(cat, parent_category);
			}

		}

		// Eliminar los categoriesTranslation
		for (final CategoryTranslation c : category.getCategoriesTranslations())
			this.categoryTranslationService.delete(c);

		this.categoryRepository.delete(category);
	}

	// Other business methods --------------------------

	// Auxiliar methods --------------------------------
	protected void updateParent(final Category category, final Category parent) {
		category.setParent(parent);
	}

	protected void addDescendantCategory(final Category category, final Category descendant) {
		Collection<Category> aux;

		aux = category.getDescendants();
		aux.add(descendant);

		category.setDescendants(aux);
	}

	protected void addDescendantCategories(final Category category, final Collection<Category> descendants) {
		Collection<Category> aux;

		aux = category.getDescendants();
		aux.addAll(descendants);

		category.setDescendants(aux);
	}

	protected void removeDescendantCategory(final Category category, final Category descendant) {
		Collection<Category> aux;

		aux = category.getDescendants();
		aux.remove(descendant);

		category.setDescendants(aux);
	}

	// Private methods ---------------------------------
	/*
	 * private Category findRootCategory() {
	 * Category result;
	 * 
	 * result = this.categoryRepository.findRootCategory();
	 * 
	 * return result;
	 * }
	 */
	private Category findOneToEdit(final int categoryId) {
		Category result;

		result = this.findOne(categoryId);

		return result;
	}
	//TODO: No se abstrae de los idiomas soportados pero funciona correctamente.
	private boolean validLanguages(final Category category) {
		final Map<String, Integer> map;
		Collection<CategoryTranslation> categoriesTranslations;
		boolean result;
		final String[] languages;
		Integer valor;
		final int n;

		map = new HashMap<String, Integer>();
		languages = CategoryTranslationService.LANGUAGES;
		categoriesTranslations = category.getCategoriesTranslations();
		result = true;
		valor = 0;
		n = languages.length;

		for (int i = 0; i < n; i++)
			map.put(languages[i], 0);

		for (final CategoryTranslation ct : categoriesTranslations) {
			valor = map.get(ct.getLanguage());
			valor++;
			map.put(ct.getLanguage(), valor);
		}

		for (final Integer i : map.values())
			result = result && i.equals(1);

		return result;
	}

}
