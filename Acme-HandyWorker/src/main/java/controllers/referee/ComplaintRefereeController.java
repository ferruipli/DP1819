
package controllers.referee;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ComplaintService;
import services.RefereeService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	// Constructors -----------------------------------------------------------

	public ComplaintRefereeController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/listSelfAssigned", method = RequestMethod.GET)
	public ModelAndView listSelfAssigned(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Complaint> complaints;
		Pageable pageable;
		PaginatedList complaintsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		complaints = this.complaintService.findSelfAssignedByPrincipal(pageable);
		complaintsAdapted = new PaginatedListAdapter(complaints, sort);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaintsAdapted);
		result.addObject("requestURI", "complaint/referee/listSelfAssigned.do");

		return result;
	}

	@RequestMapping(value = "/listNotAssigned", method = RequestMethod.GET)
	public ModelAndView listNotAssigned(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Complaint> complaints;
		Pageable pageable;
		PaginatedList complaintsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		complaints = this.complaintService.findNotAssigned(pageable);
		complaintsAdapted = new PaginatedListAdapter(complaints, sort);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaintsAdapted);
		result.addObject("requestURI", "complaint/referee/listNotAssigned.do");

		return result;
	}

	// Other business methods -------------------------------------------------

	@RequestMapping(value = "/selfAssign", method = RequestMethod.GET)
	public ModelAndView selfAssign(@RequestParam final int complaintId, final RedirectAttributes redir) {
		ModelAndView result;
		Complaint complaint;

		try {
			complaint = this.complaintService.findOne(complaintId);
			this.refereeService.selfAssignComplaint(complaint);
			result = new ModelAndView("redirect:/complaint/customer,handyWorker,referee/display.do?complaintId=" + complaintId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/complaint/customer,handyWorker,referee/display.do?complaintId=" + complaintId);
			redir.addFlashAttribute("message", "complaint.self.assign.error");
		}

		return result;
	}
}
