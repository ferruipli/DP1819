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
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping("/application/handyWorker,customer")
public class ApplicationHandyWorkerCustomerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------
	public ApplicationHandyWorkerCustomerController() {

	}

	// Application save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {

				//If status is pending and actor is handyworker he/she can edit application
				if (LoginService.getPrincipal().getAuthorities().toString().equals("[HANDYWORKER]")) {
					this.applicationService.save(application);
					result = new ModelAndView("redirect:../../application/handyWorker/list.do");

					//If actor is customer 
				} else {
					this.applicationService.save(application);
					result = new ModelAndView("redirect:../../");
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
