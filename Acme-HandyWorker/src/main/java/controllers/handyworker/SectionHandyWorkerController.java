/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyworker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section/handyWorker")
public class SectionHandyWorkerController extends AbstractController {

	@Autowired
	private SectionService	sectionService;

	@Autowired
	private TutorialService	tutorialService;


	// Constructors -----------------------------------------------------------
	public SectionHandyWorkerController() {

	}

	// Section Create ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(tutorialId);

		section = this.sectionService.create();
		result = this.createEditModelAndView(section);
		this.sectionService.addSectionToTutorial(tutorial, section);

		return result;
	}

	//Tutorial save ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(section);
		else
			try {
				this.sectionService.save(section);
				result = new ModelAndView("redirect:../../");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(section, "section.commit.error");
			}

		return result;
	}

	// Arcillary methods-----------------------------------------
	protected ModelAndView createEditModelAndView(final Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("message", messageCode);

		return result;
	}

}
