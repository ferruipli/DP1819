
package controllers.customerhandyWorkerreferee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.ReportService;
import services.UtilityService;
import controllers.AbstractController;
import domain.Report;

@Controller
@RequestMapping("report/customer,handyWorker,referee")
public class ReportMultiuserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReportService		reportService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private ComplaintService	complaintService;


	// Constructors -----------------------------------------------------------

	public ReportMultiuserController() {
		super();
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reportId) {
		ModelAndView result;
		Report report;
		Collection<String> attachments;
		boolean reportEditionPerm;

		report = this.reportService.findOne(reportId);
		attachments = this.utilityService.getSplittedAttachments(report.getAttachments());
		reportEditionPerm = this.reportService.isPrincipalCreator(report);

		if (!report.getFinalMode() && !reportEditionPerm)
			result = new ModelAndView("redirect:/welcome/index.do");
		else {
			result = new ModelAndView("report/display");
			result.addObject("report", report);
			result.addObject("attachments", attachments);
			result.addObject("reportEditionPerm", reportEditionPerm);
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int reportId) {
		ModelAndView result;
		Integer complaintId;

		complaintId = this.complaintService.findIdByReportId(reportId);
		result = new ModelAndView("redirect:/complaint/customer,handyWorker,referee/display.do?complaintId=" + complaintId);

		return result;
	}

}
