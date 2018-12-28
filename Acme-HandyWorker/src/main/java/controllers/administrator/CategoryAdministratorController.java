/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.CategoryTranslationService;
import controllers.AbstractController;
import domain.Category;
import forms.CategoryForm;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	private CategoryService				categoryService;

	@Autowired
	private CategoryTranslationService	categoryTranslationService;


	// Constructors -----------------------------------------------------------

	public CategoryAdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int categoryId, final Locale locale) {
		ModelAndView result;
		Category category, root;
		String language, name_parent, name_category;
		Map<Integer, String> category_name;

		category = this.categoryService.findOne(categoryId);
		root = this.categoryService.findRootCategory();

		language = locale.getLanguage();

		name_category = this.categoryTranslationService.findByLanguageCategory(category.getId(), language).getName();

		category_name = this.categoryService.categoriesByLanguage(category.getDescendants(), language);
		category_name.put(categoryId, name_category);

		if (!root.equals(category)) {
			name_parent = this.categoryTranslationService.findByLanguageCategory(category.getParent().getId(), language).getName();
			category_name.put(category.getParent().getId(), name_parent);
		}

		result = new ModelAndView("category/display");
		result.addObject("category", category);
		result.addObject("mapa", category_name);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Locale locale, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Collection<Category> categories;
		Map<Integer, String> category_name;
		String language;
		language = locale.getLanguage();

		categories = this.categoryService.findAll();
		category_name = this.categoryService.categoriesByLanguage(categories, language);

		result = new ModelAndView("category/list");
		result.addObject("requestURI", "category/administrator/list.do");
		result.addObject("categories", categories);
		result.addObject("mapa", category_name);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		CategoryForm categoryForm;

		categoryForm = new CategoryForm();

		result = this.createEditModelAndView(categoryForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		final ModelAndView result;
		Category category;
		CategoryForm categoryForm;
		String en_name, es_name;

		category = this.categoryService.findOne(categoryId);

		en_name = this.categoryTranslationService.findByLanguageCategory(categoryId, "en").getName();
		es_name = this.categoryTranslationService.findByLanguageCategory(categoryId, "es").getName();

		categoryForm = new CategoryForm();

		categoryForm.setId(categoryId);
		categoryForm.setParent(category.getParent());
		categoryForm.setEn_name(en_name);
		categoryForm.setEs_name(es_name);

		result = this.createEditModelAndView(categoryForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final CategoryForm categoryForm, final BindingResult binding) {
		ModelAndView result;
		String en_name, es_name;
		Category parent, category;

		parent = this.categoryService.validateParent(categoryForm, binding);
		en_name = this.categoryService.validateName("en_name", categoryForm.getEn_name(), binding);
		es_name = this.categoryService.validateName("es_name", categoryForm.getEs_name(), binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(categoryForm);
		else
			try {
				category = this.categoryService.reconstruct(categoryForm);
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(categoryForm, "category.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final CategoryForm categoryForm, final BindingResult binding) {
		ModelAndView result;
		Category category;

		try {
			category = this.categoryService.findOne(categoryForm.getId());
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(categoryForm, "category.commit.error");
		}

		return result;
	}

	// Arcillary methods --------------------------
	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm) {
		ModelAndView result;

		result = this.createEditModelAndView(categoryForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm, final String messageCode) {
		ModelAndView result;
		Collection<Category> parents;
		Category category;

		parents = this.categoryService.findAll();

		if (categoryForm.getId() != 0) {
			category = this.categoryService.findOne(categoryForm.getId());
			parents.remove(category);
		}

		result = new ModelAndView("category/edit");

		result.addObject("categoryForm", categoryForm);
		result.addObject("parents", parents);
		result.addObject("message", messageCode);

		return result;
	}

}
