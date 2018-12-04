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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TutorialService;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class ProfileController extends AbstractController {

	@Autowired
	private TutorialService	tutorialService;


	// Constructors -----------------------------------------------------------
	public ProfileController() {

	}

	// Tutorial display ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView tutorialDisplay() {
		ModelAndView result;
		Tutorial tutorial;
		final int tutorialId = 9860;
		Collection<Section> sections;
		Collection<Sponsorship> sponsorships;

		result = new ModelAndView("tutorial/display");
		tutorial = this.tutorialService.findOne(tutorialId);
		sections = tutorial.getSections();
		sponsorships = tutorial.getSponsorShips();
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("sponsorships", sponsorships);

		return result;
	}

	// Tutorial list ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView tutorialList() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		result = new ModelAndView("tutorial/list");
		tutorials = this.tutorialService.findAll();
		result.addObject("tutorials", tutorials);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("profile/action-2");

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/action-3")
	public ModelAndView action3() {
		throw new RuntimeException("Oops! An *expected* exception was thrown. This is normal behaviour.");
	}

}
