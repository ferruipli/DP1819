
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Customer;
import domain.HandyWorker;

@Controller
@RequestMapping("dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Services ------------------
	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructors --------------
	public DashboardAdministratorController() {
		super();
	}

	// methods --------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		double[] dataFixUpTaskPerUser;
		final double[] dataApplicationsPerTask;
		double[] dataMaximumPrice;
		Double[] dataOfApplicationPrice;
		Double ratPendingApp, ratAcceptedApp, ratRejectedApp, ratPendingPeriodApp;
		Collection<Customer> customers;
		Collection<HandyWorker> handyWorkers;

		// LEVEL C -----------------------------------------
		dataFixUpTaskPerUser = this.fixUpTaskService.findDataNumberFixUpTaskPerUser();
		//TODO: dataApplicationPerTask
		dataMaximumPrice = this.fixUpTaskService.findDataMaximumPrice();
		dataOfApplicationPrice = this.applicationService.findDataOfApplicationPrice();
		ratPendingApp = this.applicationService.findRatioPendingApplications();
		ratAcceptedApp = this.applicationService.findRatioAcceptedApplications();
		ratRejectedApp = this.applicationService.findRatioRejectedApplications();
		ratPendingPeriodApp = this.applicationService.findRatioPendingApplicationsNotChangeStatus();

		customers = this.customerService.customerMoreThanAverage();
		handyWorkers = this.handyWorkerService.atLeast10Application();

		result = new ModelAndView("dashboard/display");

		result.addObject("dataFixUpTaskPerUser", dataFixUpTaskPerUser);
		//TODO: result.addObject("dataApplicationPerTask", dataApplicationPerTask);
		result.addObject("dataMaximumPrice", dataMaximumPrice);
		result.addObject("dataOfApplicationPrice", dataOfApplicationPrice);
		result.addObject("ratPendingApp", ratPendingApp);
		result.addObject("ratAcceptedApp", ratAcceptedApp);
		result.addObject("ratRejectedApp", ratRejectedApp);
		result.addObject("ratPendingPeriodApp", ratPendingPeriodApp);

		result.addObject("customers", customers);
		result.addObject("handyWorkers", handyWorkers);

		return result;
	}

}
