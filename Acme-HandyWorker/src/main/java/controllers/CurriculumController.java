
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.HandyWorkerService;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	// Services --------------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private CurriculumService	curriculumService;


	// Action-1 ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int handyWorkerId) {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(this.handyWorkerService.findOne(handyWorkerId).getCurriculum().getId());
		Assert.notNull(curriculum);
		result = new ModelAndView("curriculum/display");

		result.addObject("curriculum", curriculum);

		return result;
	}
}
