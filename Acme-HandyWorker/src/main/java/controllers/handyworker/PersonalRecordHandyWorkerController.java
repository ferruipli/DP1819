
package controllers.handyworker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.PersonalRecordService;
import controllers.AbstractController;
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

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();
		result = this.createEditModelAndView(personalRecord);

		return result;

	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId) {
		ModelAndView result;
		PersonalRecord personalRecord;
		Integer handyWorkerId;

		personalRecord = this.personalRecordService.findOne(personalRecordId);
		Assert.notNull(personalRecord);
		handyWorkerId = this.handyWorkerService.findByPrincipal().getId();

		result = this.createEditModelAndView(personalRecord);
		result.addObject("handyWorkerId", handyWorkerId);

		return result;
	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
			}

		return result;

	}

	// Ancillary -------------------------------------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {

		ModelAndView result;
		result = this.createEditModelAndView(personalRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String messageCode) {
		assert personalRecord != null;

		ModelAndView result;

		result = new ModelAndView("personalRecord/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", messageCode);

		return result;

	}
}
