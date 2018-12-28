
package controllers.customerhandyworkerreferee;

import java.util.Collection;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.RefereeService;
import services.UtilityService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("complaint/customer,handyWorker,referee")
public class ComplaintMultiuserController extends AbstractController {

	// Services --------------------------------------------------------------- 
	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private RefereeService		refereeService;


	// Constructors -----------------------------------------------------------

	public ComplaintMultiuserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer fixUpTaskId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Complaint> complaints;
		Pageable pageable;
		PaginatedList complaintsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		complaints = this.complaintService.findByFixUpTaskId(fixUpTaskId, pageable);
		complaintsAdapted = new PaginatedListAdapter(complaints, sort);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaintsAdapted);
		result.addObject("requestURI", "complaint/customer,handyWorker,referee/list.do?fixUpTaskId=" + Integer.toString(fixUpTaskId));
		result.addObject("fixUpTaskId", fixUpTaskId);

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {
		ModelAndView result;
		Complaint complaint;
		Collection<String> attachments;
		boolean isAssigned, reportCreationPerm;

		complaint = this.complaintService.findOne(complaintId);
		attachments = this.utilityService.getSplittedAttachments(complaint.getAttachments());
		isAssigned = this.complaintService.isAssigned(complaintId);
		reportCreationPerm = isAssigned ? this.refereeService.principalHasSelfAssigned(complaint) : false;

		result = new ModelAndView("complaint/display");
		result.addObject("complaint", complaint);
		result.addObject("attachments", attachments);
		result.addObject("isAssigned", isAssigned);
		result.addObject("reportCreationPerm", reportCreationPerm);

		return result;
	}

}
