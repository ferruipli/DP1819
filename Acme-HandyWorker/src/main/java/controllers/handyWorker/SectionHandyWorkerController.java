/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyWorker;

import javax.servlet.http.HttpServletRequest;
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
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.create();
		result = this.createEditModelAndView(section, tutorialId);

		return result;
	}

	//Section save ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		final Tutorial tutorial;
		Integer tutorialId;
		Section saved;
		String paramTutorialId;

		paramTutorialId = request.getParameter("tutorialId");
		tutorialId = paramTutorialId == null ? null : Integer.parseInt(paramTutorialId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(section, tutorialId);
		else
			try {
				tutorial = this.tutorialService.findOne(tutorialId);
				saved = this.sectionService.save(section);
				this.sectionService.addSectionToTutorial(tutorial, saved);

				result = new ModelAndView("redirect:../../tutorial/handyWorker/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(section, tutorialId, "section.commit.error");
			}

		return result;
	}

	// Arcillary methods-----------------------------------------
	protected ModelAndView createEditModelAndView(final Section section, final Integer tutorialId) {
		ModelAndView result;

		result = this.createEditModelAndView(section, tutorialId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section, final Integer tutorialId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("tutorialId", tutorialId);
		result.addObject("messageCode", messageCode);

		return result;
	}

}
