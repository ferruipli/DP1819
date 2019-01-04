/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customerhandyWorkerreferee;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomisationService;
import services.FixUpTaskService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Application;
import domain.FixUpTask;

@Controller
@RequestMapping(value = "/application/customer,handyWorker,referee")
public class ApplicationMultiuserController extends AbstractController {

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private FixUpTaskService		fixUpTasksService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------
	public ApplicationMultiuserController() {

	}

	// Application List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Application> applications;
		final Pageable pageable;
		final PaginatedList applicationsAdapted;
		FixUpTask fixUpTask;

		pageable = this.newFixedPageable(page, dir, sort);
		fixUpTask = this.fixUpTasksService.findOne(fixUpTaskId);
		applications = this.applicationService.findApplicationByFixUpTask(fixUpTask.getId(), pageable);
		applicationsAdapted = new PaginatedListAdapter(applications, sort);

		result = new ModelAndView("application/list");
		result.addObject("applications", applicationsAdapted);
		result.addObject("requestURI", "application/customer,handyWorker,referee/list.do");

		return result;
	}

	//  APPLICATION DISPLAY---------------------------------------------------------------		
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
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
