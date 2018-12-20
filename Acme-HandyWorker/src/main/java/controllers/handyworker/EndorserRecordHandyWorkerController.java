
package controllers.handyworker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EndorserRecordService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordHandyWorkerController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;


	// Constructors ---------------------------------------

	public EndorserRecordHandyWorkerController() {
		super();
	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);
		result = new ModelAndView("endorserRecord/edit");

		result.addObject("endorserRecord", endorserRecord);

		return result;
	}
}
