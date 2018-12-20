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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.CategoryTranslationService;
import controllers.AbstractController;
import domain.Category;

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

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final int categoryId, final Locale locale) {
		ModelAndView result;
		Category category;
		String language, name_sibling, name_parent, name_category;
		Map<Integer, String> category_name;

		category = this.categoryService.findOne(categoryId);

		language = (locale.getLanguage().equals(new Locale("es").getLanguage())) ? "Español" : "Ingles";
		//TODO: Cambiar denominacion de los idiomas del sistema: es, en.

		name_category = this.categoryTranslationService.findByLanguageCategory(category.getId(), language).getName();
		name_parent = this.categoryTranslationService.findByLanguageCategory(category.getParent().getId(), language).getName();

		category_name = new HashMap<Integer, String>();
		category_name.put(categoryId, name_category);
		category_name.put(category.getParent().getId(), name_parent);

		for (final Category c : category.getDescendants()) {
			name_sibling = this.categoryTranslationService.findByLanguageCategory(c.getId(), language).getName();
			category_name.put(c.getId(), name_sibling);
		}

		result = new ModelAndView("category/display");
		result.addObject("category", category);
		result.addObject("mapa", category_name);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list(final Locale locale) {
		ModelAndView result;
		Collection<Category> categories;
		Map<Integer, String> category_name;
		String language, name_category;

		categories = this.categoryService.findAll();

		language = (locale.getLanguage().equals(new Locale("es").getLanguage())) ? "Español" : "Ingles";

		category_name = new HashMap<Integer, String>();

		for (final Category c : categories) {
			name_category = this.categoryTranslationService.findByLanguageCategory(c.getId(), language).getName();
			category_name.put(c.getId(), name_category);
		}

		result = new ModelAndView("category/list");
		result.addObject("requestURI", "category/administrator/list.do");
		result.addObject("categories", categories);
		result.addObject("mapa", category_name);

		return result;
	}
}
