/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomisationService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Application;
import domain.FixUpTask;

@Controller
@RequestMapping(value = "/application/customer")
public class ApplicationCustomerController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private CustomisationService	customisationService;
	@Autowired
	private FixUpTaskService		fixUpTasksService;


	// Constructors -----------------------------------------------------------
	public ApplicationCustomerController() {

	}

	// Application List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView applicationList(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Collection<Application> applications;
		FixUpTask fixUpTask;

		result = new ModelAndView("application/list");
		fixUpTask = this.fixUpTasksService.findOne(fixUpTaskId);
		applications = fixUpTask.getApplications();
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/list.do");

		return result;
	}

}
