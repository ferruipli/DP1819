
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.CategoryTranslation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under testing ---------------------------------
	@Autowired
	private CategoryService				categoryService;

	// Supporting services -----------------------------------
	@Autowired
	private CategoryTranslationService	categoryTranslationService;


	// Test --------------------------------------------------
	@Test
	public void testCreate() {
		super.authenticate("admin1");

		Category category;

		category = this.categoryService.create();

		Assert.notNull(category);
		Assert.notNull(category.getDescendants());
		Assert.notNull(category.getCategoriesTranslations());
		Assert.isNull(category.getParent());

		super.unauthenticate();
	}

	@Test
	public void positiveTestSave_uno() {
		super.authenticate("admin1");

		Category category;
		final Category saved;
		final Collection<Category> all;

		category = this.categoryService.create();
		category.setParent(this.getParent());
		category.setCategoriesTranslations(this.categoriesTranslation());

		super.unauthenticate();
	}

	/* Test negativo: category = null */
	@Test
	public void negativeTestSave_uno() {
		super.authenticate("admin1");

		Category category, saved;
		Collection<Category> all;

		category = null;

		saved = this.categoryService.save(category);

		all = this.categoryService.findAll();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/* Test negativo: categoria sin padre */
	@Test
	public void negativeTestSave_dos() {
		super.authenticate("admin1");

		Category category, saved;
		Collection<Category> all;

		category = this.categoryService.create();
		category.setCategoriesTranslations(this.categoriesTranslation());

		saved = this.categoryService.save(category);

		all = this.categoryService.findAll();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/* Test invalido: numero de categoyTranslation insuficiente */
	@Test
	public void negativeTestSave_tres() {
		super.authenticate("admin1");

		Category category, saved;
		Collection<Category> all;

		category = this.categoryService.create();
		category.setCategoriesTranslations(this.invalid_categoriesTranslation());

		saved = this.categoryService.save(category);

		all = this.categoryService.findAll();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	// private methods -----------------------------------------
	private List<CategoryTranslation> categoriesTranslation() {
		List<CategoryTranslation> results;

		CategoryTranslation ct1, ct2, ct1_saved, ct2_saved;

		ct1 = this.categoryTranslationService.create();
		ct1.setLanguage("Español");
		ct1.setName("Arreglar videoconsola");

		ct2 = this.categoryTranslationService.create();
		ct2.setLanguage("Ingles");
		ct2.setName("Repair videoconsole");

		ct1_saved = this.categoryTranslationService.save(ct1);
		ct2_saved = this.categoryTranslationService.save(ct2);

		results = new ArrayList<CategoryTranslation>();
		results.add(ct1_saved);
		results.add(ct2_saved);

		return results;
	}

	private List<CategoryTranslation> invalid_categoriesTranslation() {
		List<CategoryTranslation> results;

		CategoryTranslation ct, saved;

		ct = this.categoryTranslationService.create();
		ct.setLanguage("Español");
		ct.setName("Arreglar videoconsola");

		saved = this.categoryTranslationService.save(ct);

		results = new ArrayList<CategoryTranslation>();
		results.add(saved);

		return results;
	}

	private Category getParent() {
		Category result;
		int id;

		id = super.getEntityId("category2");
		result = this.categoryService.findOne(id);

		return result;
	}

}
