/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyworker;

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
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping(value = "/application/handyWorker")
public class ApplicationHandyWorkerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------
	public ApplicationHandyWorkerController() {

	}

	// Application List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Application> applications;
		final Pageable pageable;
		final PaginatedList applicationsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		applications = this.applicationService.findApplicationByHandyWorker(pageable);
		applicationsAdapted = new PaginatedListAdapter(applications, sort);

		result = new ModelAndView("application/list");
		result.addObject("applications", applicationsAdapted);
		result.addObject("requestURI", "application/handyWorker/list.do");

		return result;
	}
	// Application Edit -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.applicationService.findOne(applicationId);

		result = this.createEditModelAndView(application);

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
