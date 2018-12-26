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

import java.util.Collection;

import javax.validation.Valid;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.TutorialService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/handyWorker")
public class TutorialHandyWorkerController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors -----------------------------------------------------------
	public TutorialHandyWorkerController() {

	}

	// Tutorial list ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Tutorial> tutorials;
		final Pageable pageable;
		final PaginatedList tutorialsAdapted;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();
		pageable = this.newFixedPageable(page, dir, sort);
		tutorials = this.tutorialService.findTutorialByHandyWorker(handyWorker, pageable);
		tutorialsAdapted = new PaginatedListAdapter(tutorials, sort);

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorialsAdapted);
		result.addObject("requestURI", "tutorial/handyWorker/list.do");

		return result;
	}

	// Tutorial Create ---------------------------------------------------------------		
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.create();
		result = this.createEditModelAndView(tutorial);

		return result;
	}

	//Tutorial Edit ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		result = this.createEditModelAndView(tutorial);

		return result;
	}

	//Tutorial save ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:../../");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
			}

		return result;
	}

	//Tutorial delete ---------------------------------------------------------------		
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Tutorial tutorial, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.delete(tutorial);
				result = new ModelAndView("redirect:../../");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
			}

		return result;
	}

	// Arcillary methods-----------------------------------------
	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String messageCode) {
		ModelAndView result;
		Collection<Section> sections;

		sections = tutorial.getSections();
		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("message", messageCode);

		return result;
	}

}
