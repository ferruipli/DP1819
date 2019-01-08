
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ComplaintService;
import services.CustomerService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.NoteService;
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

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private NoteService			noteService;


	// Constructors --------------
	public DashboardAdministratorController() {
		super();
	}

	// methods --------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Double[] dataApplicationsPerTask, dataOfApplicationPrice;
		final Double[] dataComplaintsPerTask;
		Double[] dataMaximumPrice, dataFixUpTaskPerUser, dataNotesPerReport;
		Double ratPendingApp, ratAcceptedApp, ratRejectedApp, ratPendingPeriodApp, ratTaskWithComplaints;
		Collection<Customer> customers, topThreeC;
		Collection<HandyWorker> handyWorkers, topThreeHW;

		// LEVEL C -----------------------------------------

		dataFixUpTaskPerUser = this.fixUpTaskService.findDataNumberFixUpTaskPerUser();
		dataApplicationsPerTask = this.applicationService.findDataOfApplicationPerFixUpTask();
		dataMaximumPrice = this.fixUpTaskService.findDataMaximumPrice();
		dataOfApplicationPrice = this.applicationService.findDataOfApplicationPrice();
		ratPendingApp = this.applicationService.findRatioPendingApplications();
		ratAcceptedApp = this.applicationService.findRatioAcceptedApplications();
		ratRejectedApp = this.applicationService.findRatioRejectedApplications();
		ratPendingPeriodApp = this.applicationService.findRatioPendingApplicationsNotChangeStatus();

		customers = this.customerService.customerMoreThanAverage();
		handyWorkers = this.handyWorkerService.atLeast10Application();

		// LEVEL B --------------------------------------
		dataComplaintsPerTask = this.complaintService.findDataNumberComplaintPerFixUpTask();
		dataNotesPerReport = this.noteService.findDataNumberNotesPerReport();

		ratTaskWithComplaints = this.fixUpTaskService.findRatioFixUpTaskWithComplaint();

		topThreeC = this.customerService.topThreeCustomer();
		topThreeHW = this.handyWorkerService.topThreeHandyWorker();

		result = new ModelAndView("dashboard/display");
		this.setBannerHeader(result);

		// LEVEL C
		result.addObject("dataFixUpTaskPerUser", dataFixUpTaskPerUser);
		result.addObject("dataApplicationPerTask", dataApplicationsPerTask);
		result.addObject("dataMaximumPrice", dataMaximumPrice);
		result.addObject("dataOfApplicationPrice", dataOfApplicationPrice);
		result.addObject("ratPendingApp", ratPendingApp);
		result.addObject("ratAcceptedApp", ratAcceptedApp);
		result.addObject("ratRejectedApp", ratRejectedApp);
		result.addObject("ratPendingPeriodApp", ratPendingPeriodApp);

		result.addObject("customers", customers);
		result.addObject("handyWorkers", handyWorkers);

		// LEVEL B
		result.addObject("dataComplaintsPerTask", dataComplaintsPerTask);
		result.addObject("dataNotesPerReport", dataNotesPerReport);
		result.addObject("ratTaskWithComplaints", ratTaskWithComplaints);

		result.addObject("topThreeC", topThreeC);
		result.addObject("topThreeHW", topThreeHW);

		return result;
	}

}
