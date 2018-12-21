
package controllers.authenticated;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SocialProfileService;
import controllers.AbstractController;
import domain.SocialProfile;

@Controller
@RequestMapping(value = "/socialProfile/authenticated")
public class SocialProfileAuthenticatedController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;


	// Constructors -----------------------------------------------------------

	public SocialProfileAuthenticatedController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId) {
		final ModelAndView result;
		final Collection<SocialProfile> socialProfiles;

		socialProfiles = this.socialProfileService.findSocialProfilesByActor(actorId);

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfiles);
		result.addObject("actorId", actorId);
		result.addObject("requestURI", "socialProfile/authenticated/list.do?actorId=" + actorId);

		return result;

	}
}
