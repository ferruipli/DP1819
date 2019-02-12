
package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Complaint;
import domain.FixUpTask;

@Controller
@RequestMapping("complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskServoce;


	// Constructors -----------------------------------------------------------

	public ComplaintCustomerController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Complaint complaint;
		FixUpTask fixUpTask;

		complaint = this.complaintService.create();
		fixUpTask = this.fixUpTaskServoce.findOne(fixUpTaskId);
		this.complaintService.addFixUpTask(complaint, fixUpTask);

		result = this.createEditModelAndView(complaint);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint);
		else
			try {
				this.complaintService.save(complaint);
				result = new ModelAndView("redirect:/complaint/customer,handyWorker,referee/list.do?fixUpTaskId=" + complaint.getFixUpTask().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------	

	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);

		result.addObject("messageCode", messageCode);

		return result;
	}
}
