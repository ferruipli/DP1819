
package controllers.authenticated;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.EndorsableService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Customer;
import domain.Endorsable;
import domain.HandyWorker;

@Controller
@RequestMapping(value = "/actor/administrator,customer,handyWorker,referee,sponsor")
public class ActorMultiUserController extends AbstractController {

	// Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private EndorsableService	endorsableService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor

	public ActorMultiUserController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;
		Endorsable endorsable;
		Collection<Authority> authorities;
		HandyWorker handyWorker;

		actor = this.actorService.findPrincipal();
		authorities = actor.getUserAccount().getAuthorities();
		handyWorker = this.handyWorkerService.findByPrincipal();

		result = new ModelAndView("actor/display");

		if (actor instanceof Customer || actor instanceof HandyWorker) {
			endorsable = this.endorsableService.findOne(actor.getId());
			result.addObject("actor", endorsable);
			result.addObject("isEndorsable", true);
		} else {
			result.addObject("actor", actor);
			result.addObject("isEndorsable", false);
		}

		result.addObject("authorities", authorities);
		result.addObject("handyWorker", handyWorker);

		return result;
	}

}
