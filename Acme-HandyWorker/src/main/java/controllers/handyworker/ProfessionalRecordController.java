
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProfessionalRecordService;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyWorker")
public class ProfessionalRecordController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	// Constructors ---------------------------------------

	public ProfessionalRecordController() {
		super();
	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);
		result = new ModelAndView("professionalRecord/edit");

		result.addObject("professionalRecord", professionalRecord);

		return result;
	}
}
