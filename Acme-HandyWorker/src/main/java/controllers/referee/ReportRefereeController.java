
package controllers.referee;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ComplaintService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("report/referee")
public class ReportRefereeController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private ReportService		reportService;


	// Constructors -----------------------------------------------------------

	public ReportRefereeController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int complaintId) {
		ModelAndView result;
		Report report;

		report = this.reportService.create();
		result = this.createEditModelAndView(report, complaintId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		ModelAndView result;
		Report report;

		report = this.reportService.findOne(reportId);
		result = this.createEditModelAndView(report);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report report, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Complaint complaint;
		Report saved;
		Integer complaintId;
		String paramComplaintId;

		paramComplaintId = request.getParameter("complaintId");
		complaintId = paramComplaintId.isEmpty() ? null : Integer.parseInt(paramComplaintId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(report, complaintId);
		else
			try {
				if (report.getId() == 0) {
					complaint = this.complaintService.findOne(complaintId);
					saved = this.reportService.save(complaint, report);
				} else
					saved = this.reportService.save(report);

				result = new ModelAndView("redirect:/report/customer,handyWorker,referee/display.do?reportId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(report, complaintId, "report.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Report report, final BindingResult binding) {
		ModelAndView result;
		int complaintId;

		try {
			complaintId = this.complaintService.findIdByReportId(report.getId());
			this.reportService.delete(report);
			result = new ModelAndView("redirect:/complaint/customer,handyWorker,referee/display.do?complaintId=" + complaintId);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(report, "report.commit.error");
		}

		return result;
	}

	// Other business methods -------------------------------------------------

	@RequestMapping(value = "/makeFinal", method = RequestMethod.GET)
	public ModelAndView makeFinal(@RequestParam final int reportId, final RedirectAttributes redir) {
		ModelAndView result;
		Report report;

		try {
			report = this.reportService.findOne(reportId);
			this.reportService.makeFinal(report);
			result = new ModelAndView("redirect:/report/customer,handyWorker,referee/display.do?reportId=" + reportId);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/report/customer,handyWorker,referee/display.do?reportId=" + reportId);
			redir.addFlashAttribute("messageCode", "report.make.final.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final Integer complaintId) {
		ModelAndView result;

		result = this.createEditModelAndView(report, complaintId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final String messageCode) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null, messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final Integer complaintId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("complaintId", complaintId);

		result.addObject("messageCode", messageCode);

		return result;
	}
}
