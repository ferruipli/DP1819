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

import java.util.ArrayList;
import java.util.Collection;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.TutorialService;
import utilities.internal.PaginatedListAdapter;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors -----------------------------------------------------------
	public TutorialController() {

	}
	// Tutorial display ---------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;
		Collection<Section> sections;
		Collection<Sponsorship> sponsorships;
		Sponsorship sponsorship;

		tutorial = this.tutorialService.findOne(tutorialId);
		sections = tutorial.getSections();
		sponsorships = tutorial.getSponsorShips();
		sponsorship = this.getRandomSponsorship(sponsorships);

		result = new ModelAndView("tutorial/display");
		result.addObject("tutorial", tutorial);
		result.addObject("sections", sections);
		result.addObject("sponsorships", sponsorships);

		if (sponsorship != null)
			result.addObject("sponsorship", sponsorship);

		return result;
	}

	// Tutorial list ---------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Tutorial> tutorials;
		final Pageable pageable;
		final PaginatedList tutorialsAdapted;
		Integer principalId;

		pageable = this.newFixedPageable(page, dir, sort);
		tutorials = this.tutorialService.findAllTutorialPageable(pageable);
		tutorialsAdapted = new PaginatedListAdapter(tutorials, sort);
		principalId = null;

		try {
			principalId = this.handyWorkerService.findByPrincipal().getId();
		} catch (final Exception e) {

		}

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorialsAdapted);
		result.addObject("principalId", principalId);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}
	// Tutorial list by handyWorker---------------------------------------------------------------		

	@RequestMapping(value = "/listHandyWorker", method = RequestMethod.GET)
	public ModelAndView listHandyWorker(@RequestParam final int handyWorkerId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Tutorial> tutorials;
		final Pageable pageable;
		final PaginatedList tutorialsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		tutorials = this.tutorialService.findTutorialByHandyWorker(handyWorkerId, pageable);
		tutorialsAdapted = new PaginatedListAdapter(tutorials, sort);

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorialsAdapted);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}

	private Sponsorship getRandomSponsorship(final Collection<Sponsorship> sponsorships) {
		Sponsorship sponsorship;
		sponsorship = null;
		final ArrayList<Sponsorship> lista = new ArrayList<Sponsorship>(sponsorships);
		if (sponsorships.size() != 0) {
			final double random = Math.random() * (sponsorships.size() - 1);
			final int intRandom = (int) random;
			sponsorship = lista.get(intRandom);
		}
		return sponsorship;
	}
}
