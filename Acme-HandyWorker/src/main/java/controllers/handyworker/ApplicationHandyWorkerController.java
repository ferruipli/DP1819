/*
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyworker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import controllers.AbstractController;
import domain.Application;

@Controller
@RequestMapping(value = "/application/handyworker")
public class ApplicationHandyWorkerController extends AbstractController {

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------------------
	public ApplicationHandyWorkerController() {

	}

	// Application List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView applicationList() {
		ModelAndView result;
		Collection<Application> applications;

		result = new ModelAndView("application/list");
		applications = this.applicationService.findApplicationByHandyWorker();
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/handyworker/list.do");

		return result;
	}

}
