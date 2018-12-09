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

import services.ApplicationService;
import services.FinderService;
import services.FixUpTaskService;
import services.TutorialService;
import domain.Application;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private FinderService		finderService;


	// Constructors -----------------------------------------------------------
	public ProfileController() {

	}

	// Tutorial display ---------------------------------------------------------------		

	@RequestMapping(value = "/tutorial/display", method = RequestMethod.GET)
	public ModelAndView tutorialDisplay() {
		ModelAndView result;
		Tutorial tutorial;
		final int tutorialId = 9594;
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

	@RequestMapping(value = "/tutorial/list", method = RequestMethod.GET)
	public ModelAndView tutorialList() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		result = new ModelAndView("tutorial/list");
		tutorials = this.tutorialService.findAll();
		result.addObject("tutorials", tutorials);

		return result;
	}

	// Tutorial Create ---------------------------------------------------------------		
	@RequestMapping(value = "/tutorial/create", method = RequestMethod.GET)
	public ModelAndView tutorialCreate() {
		ModelAndView result;
		Tutorial tutorial;

		result = new ModelAndView("tutorial/edit");
		tutorial = this.tutorialService.create();
		result.addObject("tutorial", tutorial);

		return result;
	}

	//  APPLICATION EDIT---------------------------------------------------------------		
	@RequestMapping(value = "/application/edit", method = RequestMethod.GET)
	public ModelAndView applicationEdit() {
		ModelAndView result;
		Application application;
		Double VAT;

		result = new ModelAndView("application/edit");
		application = this.applicationService.findOne(9691);
		result.addObject("application", application);
		VAT = 21.;
		result.addObject("VAT", VAT);

		return result;

	}

	//  APPLICATION DISPLAY---------------------------------------------------------------		
	@RequestMapping(value = "/application/display", method = RequestMethod.GET)
	public ModelAndView applicationDisplay() {
		ModelAndView result;
		Application application;
		Double VAT;

		result = new ModelAndView("application/display");
		application = this.applicationService.findOne(9691);
		VAT = 21.;
		result.addObject("application", application);
		result.addObject("VAT", VAT);
		return result;
	}

}
