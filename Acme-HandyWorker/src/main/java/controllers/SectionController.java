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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import domain.Section;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	@Autowired
	private SectionService	sectionService;


	// Constructors -----------------------------------------------------------
	public SectionController() {

	}
	// Section display ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sectionId) {
		ModelAndView result;
		Section section;

		result = new ModelAndView("section/display");
		section = this.sectionService.findOne(sectionId);
		result.addObject("section", section);

		return result;
	}

}
