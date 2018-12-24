
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

import services.EndorserRecordService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.EndorserRecord;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordHandyWorkerController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Constructors ---------------------------------------

	public EndorserRecordHandyWorkerController() {
		super();
	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord);

		return result;

	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;
		Integer handyWorkerId;

		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);
		result = new ModelAndView("endorserRecord/edit");
		handyWorkerId = this.handyWorkerService.findByPrincipal().getId();

		result.addObject("endorserRecord", endorserRecord);
		result.addObject("handyWorkerId", handyWorkerId);

		return result;
	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:/curriculum/display.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:../../curriculum/display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
		}

		return result;
	}

	// Ancillary -------------------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {

		ModelAndView result;
		result = this.createEditModelAndView(endorserRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		assert endorserRecord != null;

		ModelAndView result;

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", messageCode);

		return result;
	}
}
