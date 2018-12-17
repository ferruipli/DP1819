
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping(value = "/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorAdministratorController() {

	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Actor> actors;

		actors = this.actorService.findAllSuspicious();

		result = new ModelAndView("actor/list");

		result.addObject("actors", actors);

		return result;

	}

}
