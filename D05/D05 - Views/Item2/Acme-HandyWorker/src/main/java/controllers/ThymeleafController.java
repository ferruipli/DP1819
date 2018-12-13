/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;

@Controller
@RequestMapping("fixUpTaskThymeleaf/")
public class ThymeleafController extends AbstractController {

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Panic handler ----------------------------------------------------------

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView fixUpTasks() {
		ModelAndView result;

		result = new ModelAndView("fixUpTaskThymeleaf/display");
		result.addObject("fixUpTasks", this.fixUpTaskService.findAll());

		return result;
	}

}
