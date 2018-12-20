
package controllers.handyworker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.PersonalRecordService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordHandyWorkerController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Constructors ---------------------------------------

	public PersonalRecordHandyWorkerController() {
		super();
	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord personalRecord;
		HandyWorker handyWorker;
		Integer handyWorkerId;

		personalRecord = this.personalRecordService.findOne(personalRecordId);
		handyWorker = this.handyWorkerService.findByPrincipal();
		handyWorkerId = handyWorker.getId();
		Assert.notNull(personalRecord);
		result = new ModelAndView("personalRecord/edit");

		result.addObject("personalRecord", personalRecord);
		result.addObject("handyWorkerId", handyWorkerId);

		return result;
	}
}
