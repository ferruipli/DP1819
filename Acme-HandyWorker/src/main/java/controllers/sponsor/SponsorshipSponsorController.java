/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.sponsor;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.SponsorService;
import services.SponsorshipService;
import services.TutorialService;
import controllers.AbstractController;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipSponsorController extends AbstractController {

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private TutorialService			tutorialService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private SponsorService			sponsorService;


	// Constructors -----------------------------------------------------------
	public SponsorshipSponsorController() {

	}

	// Sponsorship create -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Sponsorship sponsorship;
		List<String> brandName;

		sponsorship = this.sponsorshipService.create();
		brandName = (List<String>) this.customisationService.find().getCreditCardMakes();
		result = this.createEditModelAndView(sponsorship, tutorialId);
		result.addObject("brandName", brandName);

		return result;
	}
	// Sponsorship display -----------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		result = new ModelAndView("sponsorship/display");
		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		result.addObject("sponsorship", sponsorship);

		return result;
	}
	// Sponsorship list -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Sponsor sponsor;

		sponsor = this.sponsorService.findByPrincipal();
		sponsorships = sponsor.getSponsorships();

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}

	// Sponsorship save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		final Tutorial tutorial;
		final Sponsorship saved;
		Integer tutorialId;
		String paramTutorialId;

		paramTutorialId = request.getParameter("tutorialId");
		tutorialId = paramTutorialId == null ? null : Integer.parseInt(paramTutorialId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship, tutorialId);
		else
			try {
				tutorial = this.tutorialService.findOne(tutorialId);
				saved = this.sponsorshipService.save(sponsorship);
				this.sponsorshipService.addSponsorShipToTutorial(saved, tutorial);

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
