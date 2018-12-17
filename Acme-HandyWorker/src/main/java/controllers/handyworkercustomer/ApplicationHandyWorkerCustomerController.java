/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyworkercustomer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping(value = "/application/handyWorker,customer")
public class ApplicationHandyWorkerCustomerController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private LoginService			loginService;


	// Constructors -----------------------------------------------------------
	public ApplicationHandyWorkerCustomerController() {

	}

	//  APPLICATION DISPLAY---------------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView applicationDisplay(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Double VAT;

		result = new ModelAndView("application/display");
		application = this.applicationService.findOne(applicationId);
		VAT = this.customisationService.find().getVAT();
		result.addObject("application", application);
		result.addObject("VAT", VAT);

		return result;
	}

	// Application Edit save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {

				//If status is pending and actor is handyworker he/she can edit application
				if (application.getStatus().equals("PENDING") && LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER")) {
					this.applicationService.save(application);
					result = new ModelAndView("redirect:../../index.do");

					//If status is not pending and actor is handyworker he/she only can add comment
				} else if (!(application.getStatus().equals("PENDING")) && LoginService.getPrincipal().getAuthorities().contains("HANDYWORKER")) {
					this.applicationService.addCommentHandyWorker(application);
					result = new ModelAndView("redirect:../../index.do");

					//If actor is customer he/she only can add comment
				} else {
					this.applicationService.addCommentCustomer(application);
					result = new ModelAndView("redirect:../../index.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}

		return result;
	}

	// Arcillary methods-----------------------------------------
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", messageCode);

		return result;
	}

}
