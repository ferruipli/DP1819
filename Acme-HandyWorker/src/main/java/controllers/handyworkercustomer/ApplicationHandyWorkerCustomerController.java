/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyworkercustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomisationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping(value = "/application/handyworkercustomer")
public class ApplicationHandyWorkerCustomerController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomisationService	customisationService;


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

}
