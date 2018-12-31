/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customerhandyWorkerreferee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.FixUpTask;

@Controller
@RequestMapping("fixUpTask/customer,handyWorker,referee")
public class FixUpTaskMultiuserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskMultiuserController() {
		super();
	}

	// Display ----------------------------------------------------------------		

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;
		//CategoryTranslation categoryTranslation;
		double vat;
		boolean isWorkable;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		//categoryTranslation = new ArrayList<>(fixUpTask.getCategory().getCategoriesTranslations()).get(0);
		vat = this.customisationService.find().getVAT();
		isWorkable = this.fixUpTaskService.hasAcceptedApplication(fixUpTaskId);

		result = new ModelAndView("fixUpTask/display");
		result.addObject(fixUpTask);
		// TODO: result.addObject("category", categoryTranslation);
		result.addObject("isWorkable", isWorkable);
		result.addObject("vat", vat);

		return result;
	}

}
