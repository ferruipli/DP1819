/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.sponsor;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import services.SponsorshipService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping(value = "/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private TutorialService		tutorialService;


	// Constructors -----------------------------------------------------------
	public SponsorshipSponsorController() {

	}

	// Sponsorship create -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create();
		result = this.createEditModelAndView(sponsorship, tutorialId);

		return result;
	}

	// Sponsorship save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		final Tutorial tutorial;
		final Sponsorship saved;
		Integer tutorialId;

		tutorialId = null;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship, tutorialId);
		else
			try {
				if (sponsorship.getId() == 0) {
					tutorialId = Integer.parseInt(request.getParameter("tutorialId"));
					tutorial = this.tutorialService.findOne(tutorialId);
					saved = this.sponsorshipService.save(sponsorship);
					this.sponsorshipService.addSponsorShipToTutorial(saved, tutorial);
				} else
					saved = this.sponsorshipService.save(sponsorship);

				result = new ModelAndView("redirect:../../tutorial/list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, tutorialId, "sponsorship.commit.error");
			}

		return result;
	}

	// Arcillary methods-----------------------------------------
	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final Integer tutorialId) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, tutorialId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final Integer tutorialId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("tutorialId", tutorialId);
		result.addObject("message", messageCode);

		return result;
	}
}
