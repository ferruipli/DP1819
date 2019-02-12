
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

import services.EndorsableService;
import utilities.internal.PaginatedListAdapter;
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Endorsable> endorsables;
		Pageable pageable;
		PaginatedList paginatedEndorsables;

		pageable = this.newFixedPageable(page, dir, sort);

		endorsables = this.endorsableService.paginatedFindAll(pageable);

		paginatedEndorsables = new PaginatedListAdapter(endorsables, sort);

		result = new ModelAndView("actor/list");
		result.addObject("actors", paginatedEndorsables);
		result.addObject("requestURI", "endorsable/administrator/list.do");
		result.addObject("isEndorsable", true);

		return result;
	}

	@RequestMapping(value = "/computeScore", method = RequestMethod.POST, params = "compute")
	public ModelAndView computeScore() {
		ModelAndView result;

		try {
			this.endorsableService.computingScoreProcess();
		} catch (final Throwable oops) {
		}

		result = new ModelAndView("redirect:list.do");

		return result;
	}

}
