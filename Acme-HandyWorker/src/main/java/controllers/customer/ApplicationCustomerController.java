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
	private FixUpTaskService		fixUpTasksService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------
	public ApplicationCustomerController() {

	}

	// Application List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId) {
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

	// Application Edit -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		Collection<String> brandName;

		application = this.applicationService.findOne(applicationId);
		brandName = this.customisationService.find().getCreditCardMakes();

		result = this.createEditModelAndView(application);
		result.addObject("brandName", brandName);

		return result;
	}

	// Application cancel -----------------------------------------------------------

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(applicationId);

		try {
			this.applicationService.changeStatus(application);
			result = new ModelAndView("redirect:../../fixUpTask/list.do");
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
