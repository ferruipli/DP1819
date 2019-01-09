
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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


	// Constructor

	public CurriculumController() {
		super();
	}

	// Creating ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int handyWorkerId) {
		ModelAndView result;
		Curriculum curriculum;
		Integer handyWorkerLoginId = null;

		try {
			handyWorkerLoginId = this.handyWorkerService.findByPrincipal().getId();
		} catch (final Throwable ups) {
		}
		curriculum = this.curriculumService.findOne(this.handyWorkerService.findOne(handyWorkerId).getCurriculum().getId());

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("handyWorkerCurriculumId", handyWorkerId);
		result.addObject("requestURI", "curriculum/display.do");
		if (handyWorkerLoginId != null)
			result.addObject("handyWorkerLoginId", handyWorkerLoginId);

		return result;
	}
}
