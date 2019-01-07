
package controllers.handyWorker;

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
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Complaint;

@Controller
@RequestMapping("complaint/handyWorker")
public class ComplaintHandyWorkerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;


	// Constructors -----------------------------------------------------------

	public ComplaintHandyWorkerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Complaint> complaints;
		Pageable pageable;
		PaginatedList complaintsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		complaints = this.complaintService.findInvolvedByHandyWorkerPrincipal(pageable);
		complaintsAdapted = new PaginatedListAdapter(complaints, sort);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaintsAdapted);
		result.addObject("requestURI", "complaint/handyWorker/list.do");

		return result;
	}
}
