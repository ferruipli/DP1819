
package controllers.administrator;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping(value = "/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Actor> actors;
		Pageable pageable;
		PaginatedList actorsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		actors = this.actorService.findAllSuspicious(pageable);
		actorsAdapted = new PaginatedListAdapter(actors, sort);

		result = new ModelAndView("actor/list");

		result.addObject("actors", actorsAdapted);
		result.addObject("requestURI", "actor/administrator/list.do");
		result.addObject("isEndorsable", false);
		return result;
	}

	// Ban

	@RequestMapping(value = "/changeBan", method = RequestMethod.GET)
	public ModelAndView changeBan(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);

		try {
			this.actorService.changeBan(actor);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;
	}
}
