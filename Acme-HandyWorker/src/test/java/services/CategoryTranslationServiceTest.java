
package services;

import java.util.Collection;

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
public class CategoryTranslationServiceTest extends AbstractTest {

	// Services under testing ---------------------------------------------
	@Autowired
	private CategoryTranslationService	categoryTranslationService;

	// Supporting services ------------------------------------------------
	@Autowired
	private CategoryService				categoryService;


	// Tests --------------------------------------------------------------

	//TODO: A�adir las excepciones que se esperan capturar cuando se ejecuten
	// los test negativos

	@Test
	public void testCreate() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation;

		categoryTranslation = this.categoryTranslationService.create();

		Assert.notNull(categoryTranslation);
		Assert.isNull(categoryTranslation.getLanguage());
		Assert.isNull(categoryTranslation.getName());

		super.unauthenticate();
	}

	/* Test positivo en el que se inserta en la BD un objeto valido */
	@Test
	public void positiveTestSave_uno() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation, saved;
		Collection<CategoryTranslation> all;

		categoryTranslation = this.categoryTranslationService.create();
		categoryTranslation.setName("Bricomania");
		categoryTranslation.setLanguage("Espa�ol");

		saved = this.categoryTranslationService.save(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(all.contains(saved));

		super.unauthenticate();
	}

	/*
	 * Test negativo en el que se trata de insertar en la BD un objeto
	 * cuyos atributos CategoryTranslation::language y
	 * CategoryTranslation::name coinciden con los de otro objeto del
	 * mismo tipo
	 */
	@Test
	public void negativeTestSave_uno() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation, saved;
		Collection<CategoryTranslation> all;

		categoryTranslation = this.categoryTranslationService.create();
		categoryTranslation.setName("Carpinteria");
		categoryTranslation.setLanguage("Espa�ol");

		saved = this.categoryTranslationService.save(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/*
	 * Test negativo en el se prueba a insertar una categoryTranslation
	 * a null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_dos() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation, saved;
		Collection<CategoryTranslation> all;

		categoryTranslation = null;

		saved = this.categoryTranslationService.save(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/* Test negativo: idioma no soportado */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_tres() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation, saved;
		Collection<CategoryTranslation> all;

		categoryTranslation = this.categoryTranslationService.create();
		categoryTranslation.setName("Menuiserie");
		categoryTranslation.setLanguage("Frances");

		saved = this.categoryTranslationService.save(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/* Test positivo en el se borra un objeto valido */
	@Test
	public void positiveTestDelete_uno() {
		super.authenticate("admin1");

		final int id = super.getEntityId("categoryTranslation30");
		CategoryTranslation categoryTranslation;
		Collection<CategoryTranslation> all;

		categoryTranslation = this.categoryTranslationService.findOne(id);

		this.categoryTranslationService.delete(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(!all.contains(categoryTranslation));

		super.unauthenticate();
	}

	/*
	 * Test negativo: se intenta borrar un objeto que no existe
	 * en la BD
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestDelete_uno() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation;
		Collection<CategoryTranslation> all;

		categoryTranslation = this.categoryTranslationService.create();
		categoryTranslation.setName("Fibra optica");
		categoryTranslation.setLanguage("Espa�ol");

		this.categoryTranslationService.delete(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(!all.contains(categoryTranslation));

		super.unauthenticate();
	}

	/*
	 * Test negativo: no se puede borrar un objeto que no existe en
	 * la BD y que, ademas, es nulo
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestDelete_dos() {
		super.authenticate("admin1");

		CategoryTranslation categoryTranslation;
		Collection<CategoryTranslation> all;

		categoryTranslation = null;

		this.categoryTranslationService.delete(categoryTranslation);

		all = this.categoryTranslationService.findAll();

		Assert.isTrue(!all.contains(categoryTranslation));

		super.unauthenticate();
	}

	@Test
	public void testfindByLanguageCategory() {
		int id;
		String language;
		final CategoryTranslation categoryTranslation;
		Category category;

		id = super.getEntityId("category5");
		language = "Espa�ol";
		category = this.categoryService.findOne(id);
		categoryTranslation = this.categoryTranslationService.findByLanguageCategory(id, language);

		Assert.notNull(categoryTranslation);
		Assert.isTrue(category.getCategoriesTranslations().contains(categoryTranslation));
		Assert.isTrue(categoryTranslation.getLanguage().equals(language));
	}
}