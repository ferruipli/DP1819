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
import services.TutorialService;
import domain.Application;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
public class ProfileController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------
	public ProfileController() {

	}

	// Tutorial display ---------------------------------------------------------------		

	@RequestMapping(value = "/tutorial/display", method = RequestMethod.GET)
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

	@RequestMapping(value = "/tutorial/list", method = RequestMethod.GET)
	public ModelAndView tutorialList() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		result = new ModelAndView("tutorial/list");
		tutorials = this.tutorialService.findAll();
		result.addObject("tutorials", tutorials);

		return result;
	}

	// Create ---------------------------------------------------------------		
	@RequestMapping(value = "/tutorial/create", method = RequestMethod.GET)
	public ModelAndView tutorialCreate() {
		ModelAndView result;
		Tutorial tutorial;

		result = new ModelAndView("tutorial/edit");
		tutorial = this.tutorialService.create();
		result.addObject("tutorial", tutorial);

		return result;
	}

	//Edit ---------------------------------------------------------------		
	@RequestMapping(value = "/tutorial/edit", method = RequestMethod.GET)
	public ModelAndView tutorialEdit() {
		ModelAndView result;
		Tutorial tutorial;
		final int id = 9860;
		Collection<Section> sections;

		result = new ModelAndView("tutorial/edit");
		tutorial = this.tutorialService.findOne(id);
		sections = tutorial.getSections();
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);

		return result;
	}
	//  APPLICATION LIST---------------------------------------------------------------		
	@RequestMapping(value = "/application/list", method = RequestMethod.GET)
	public ModelAndView applicationList() {
		ModelAndView result;
		Collection<Application> applications;

		result = new ModelAndView("application/list");
		applications = this.applicationService.findAll();
		result.addObject("applications", applications);

		return result;
	}
}
