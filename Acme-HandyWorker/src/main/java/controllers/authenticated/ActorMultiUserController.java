
package controllers.authenticated;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping(value = "/actor/administrator,customer,handyWorker,referee,sponsor")
public class ActorMultiUserController extends AbstractController {

	// Services

	@Autowired
	private ActorService	actorService;


	// Constructor

	public ActorMultiUserController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;
		Collection<Authority> authorities;

		actor = this.actorService.findPrincipal();
		authorities = actor.getUserAccount().getAuthorities();

		result = new ModelAndView("actor/display");

		result.addObject("actor", actor);
		result.addObject("authorities", authorities);

		return result;
	}

}
