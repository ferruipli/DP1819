
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EndorsableService;
import controllers.AbstractController;
import domain.Endorsable;

@Controller
@RequestMapping("endorsable/administrator")
public class EndorsableAdministratorController extends AbstractController {

	@Autowired
	private EndorsableService	endorsableService;


	public EndorsableAdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorsableId) {
		ModelAndView result;
		Endorsable endorsable;

		endorsable = this.endorsableService.findOne(endorsableId);

		result = new ModelAndView("actor/display");
		result.addObject("actor", endorsable);
		result.addObject("isEndorsable", true);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Endorsable> endorsables;

		endorsables = this.endorsableService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("actors", endorsables);
		result.addObject("requestURI", "endorsable/administrator/list.do");
		result.addObject("isEndorsable", true);

		return result;
	}

	@RequestMapping(value = "/computeScore", method = RequestMethod.GET)
	public ModelAndView computeScore(@RequestParam final int endorsableId) {
		ModelAndView result;
		Endorsable endorsable;

		endorsable = this.endorsableService.findOne(endorsableId);

		try {
			this.endorsableService.computeScore(endorsable);
		} catch (final Throwable oops) {
		}

		result = new ModelAndView("redirect:list.do");

		return result;
	}

}
