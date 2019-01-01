/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.WarrantyService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	//	@Autowired
	//	private CategoryService		categoryService;

	@Autowired
	private WarrantyService		warrantyService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskCustomerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<FixUpTask> fixUpTasks;
		Pageable pageable;
		PaginatedListAdapter fixUpTasksAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		fixUpTasks = this.fixUpTaskService.findByCustomerPrincipal(pageable);
		fixUpTasksAdapted = new PaginatedListAdapter(fixUpTasks, sort);

		result = new ModelAndView("fixUpTask/list");
		result.addObject("requestURI", "fixUpTask/customer/list.do");
		result.addObject("fixUpTasks", fixUpTasksAdapted);

		return result;
	}

	// Create -----------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.create();
		// TODO: me queda por pasarle las categorías
		result = this.createEditModelAndView(fixUpTask);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		// TODO: me queda por pasarle las categorías
		result = this.createEditModelAndView(fixUpTask);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;
		FixUpTask saved;

		if (binding.hasErrors())
			result = this.createEditModelAndView(fixUpTask);
		else
			try {
				saved = this.fixUpTaskService.save(fixUpTask);
				result = new ModelAndView("redirect:/fixUpTask/customer,handyWorker,referee/display.do?fixUpTaskId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		try {
			this.fixUpTaskService.delete(fixUpTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fixUpTask, "fixUpTask.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;

		result = this.createEditModelAndView(fixUpTask, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fixUpTask, final String messageCode) {
		ModelAndView result;
		Collection<Warranty> warranties;

		warranties = this.warrantyService.findFinalWarranties();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);

		result.addObject("message", messageCode);

		return result;
	}
}
