
package controllers.handyworker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EducationRecordService;
import controllers.AbstractController;
import domain.EducationRecord;

@Controller
@RequestMapping("/educationRecord/handyWorker")
public class EducationRecordHandyWorkerController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;


	// Constructors ---------------------------------------

	public EducationRecordHandyWorkerController() {
		super();
	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);
		result = new ModelAndView("educationRecord/edit");

		result.addObject("educationRecord", educationRecord);

		return result;
	}
}
