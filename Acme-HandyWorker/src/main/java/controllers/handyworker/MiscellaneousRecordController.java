
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MiscellaneousRecordService;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/handyWorker")
public class MiscellaneousRecordController extends AbstractController {

	// Services -------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	// Constructors ---------------------------------------

	public MiscellaneousRecordController() {
		super();
	}

	// Edition -------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);
		result = new ModelAndView("miscellaneousRecord/edit");

		result.addObject("miscellaneousRecord", miscellaneousRecord);

		return result;
	}
}
