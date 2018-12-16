
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PersonalRecordService;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Constructors ---------------------------------------

	public PersonalRecordController() {
		super();
	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.findOne(personalRecordId);
		Assert.notNull(personalRecord);
		result = new ModelAndView("personalRecord/edit");

		result.addObject("personalRecord", personalRecord);

		return result;
	}
}
