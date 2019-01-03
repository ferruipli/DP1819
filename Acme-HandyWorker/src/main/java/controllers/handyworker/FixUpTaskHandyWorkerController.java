
package controllers.handyworker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomisationService;
import services.FinderService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.FixUpTask;

@Controller
@RequestMapping("fixUpTask/handyWorker")
public class FixUpTaskHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------

	public FixUpTaskHandyWorkerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<FixUpTask> fixUpTasks;
		Pageable pageable;
		PaginatedListAdapter fixUpTasksAdapted;
		final Boolean lastUpdateFinder;

		pageable = this.newFixedPageable(page, dir, sort);
		fixUpTasks = this.fixUpTaskService.findAll(pageable);
		fixUpTasksAdapted = new PaginatedListAdapter(fixUpTasks, sort);
		lastUpdateFinder = this.finderService.compareTime(this.handyWorkerService.findByPrincipal().getFinder().getLastUpdate(), this.customisationService.find().getTimeCachedFinderResults());

		result = new ModelAndView("fixUpTask/list");
		result.addObject("requestURI", "fixUpTask/handyWorker/listAll.do");
		result.addObject("fixUpTasks", fixUpTasksAdapted);
		result.addObject("lastUpdateFinder", lastUpdateFinder);

		return result;
	}

	@RequestMapping(value = "/listInvolved", method = RequestMethod.GET)
	public ModelAndView listInvolved(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<FixUpTask> fixUpTasks;
		Pageable pageable;
		PaginatedListAdapter fixUpTasksAdapted;
		Boolean lastUpdateFinder;

		pageable = this.newFixedPageable(page, dir, sort);
		fixUpTasks = this.fixUpTaskService.findWorkableByHandyWorkerPrincipal(pageable);
		fixUpTasksAdapted = new PaginatedListAdapter(fixUpTasks, sort);
		lastUpdateFinder = this.finderService.compareTime(this.handyWorkerService.findByPrincipal().getFinder().getLastUpdate(), this.customisationService.find().getTimeCachedFinderResults());

		result = new ModelAndView("fixUpTask/list");
		result.addObject("requestURI", "fixUpTask/customer/listInvolved.do");
		result.addObject("fixUpTasks", fixUpTasksAdapted);
		result.addObject("lastUpdateFinder", lastUpdateFinder);

		return result;
	}
	@RequestMapping(value = "/listFinder", method = RequestMethod.GET)
	public ModelAndView listFinder(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<FixUpTask> fixUpTasks;
		Pageable pageable;
		final PaginatedListAdapter fixUpTasksAdapted;
		final Boolean lastUpdateFinder;

		pageable = this.newFixedPageable(page, dir, sort);
		fixUpTasks = this.fixUpTaskService.findFixUpTaskFinderPaged(pageable);
		fixUpTasksAdapted = new PaginatedListAdapter(fixUpTasks, sort);
		lastUpdateFinder = this.finderService.compareTime(this.handyWorkerService.findByPrincipal().getFinder().getLastUpdate(), this.customisationService.find().getTimeCachedFinderResults());

		result = new ModelAndView("fixUpTask/list");
		result.addObject("requestURI", "fixUpTask/handyWorker/listAll.do");
		result.addObject("fixUpTasks", fixUpTasksAdapted);
		result.addObject("lastUpdateFinder", lastUpdateFinder);

		return result;
	}

}
