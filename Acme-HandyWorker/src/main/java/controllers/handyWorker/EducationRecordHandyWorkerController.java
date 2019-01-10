
package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EducationRecordService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.EducationRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/educationRecord/handyWorker")
public class EducationRecordHandyWorkerController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Constructors ---------------------------------------

	public EducationRecordHandyWorkerController() {
		super();
	}

	//Creating----------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();
		result = this.createEditModelAndView(educationRecord);

		return result;

	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		ModelAndView result;
		EducationRecord educationRecord;
		Integer handyWorkerId;

		educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);
		result = new ModelAndView("educationRecord/edit");
		handyWorkerId = this.handyWorkerService.findByPrincipal().getId();

		result.addObject("educationRecord", educationRecord);
		result.addObject("handyWorkerId", handyWorkerId);

		return result;
	}

	//Saving-----------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding) {

		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);
				result = new ModelAndView("redirect:/curriculum/display.do?handyWorkerId=" + handyWorker.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		try {
			this.educationRecordService.delete(educationRecord);
			result = new ModelAndView("redirect:../../curriculum/display.do?handyWorkerId=" + handyWorker.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
		}

		return result;
	}

	// Ancillary -------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {

		ModelAndView result;
		result = this.createEditModelAndView(educationRecord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String messageCode) {
		assert educationRecord != null;

		ModelAndView result;
		Integer handyWorkerId;
		handyWorkerId = this.handyWorkerService.findByPrincipal().getId();

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("messageCode", messageCode);
		result.addObject("handyWorkerId", handyWorkerId);

		return result;

	}
}
