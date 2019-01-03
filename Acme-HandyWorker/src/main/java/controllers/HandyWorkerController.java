
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.EndorsableService;
import services.HandyWorkerService;
import domain.Endorsable;
import domain.HandyWorker;

@Controller
@RequestMapping(value = "/handyWorker")
public class HandyWorkerController extends AbstractController {

	// Services

	@Autowired
	private EndorsableService	endorsableService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor

	public HandyWorkerController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) {
		ModelAndView result;
		Endorsable endorsable;
		Collection<Authority> authorities;
		HandyWorker handyWorker;

		endorsable = this.endorsableService.findOne(actorId);
		authorities = endorsable.getUserAccount().getAuthorities();
		handyWorker = this.handyWorkerService.findOne(actorId);

		result = new ModelAndView("actor/display");
		result.addObject("actor", endorsable);
		result.addObject("isEndorsable", true);

		result.addObject("authorities", authorities);
		result.addObject("curriculum", handyWorker.getCurriculum());

		return result;
	}

}