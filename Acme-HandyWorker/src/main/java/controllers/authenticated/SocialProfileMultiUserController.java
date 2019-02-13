
package controllers.authenticated;

import javax.validation.Valid;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialProfileService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping(value = "/socialProfile/administrator,customer,handyWorker,referee,sponsor")
public class SocialProfileMultiUserController extends AbstractController {

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public SocialProfileMultiUserController() {
		super();
	}

	// List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int actorId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		final ModelAndView result;
		final Page<SocialProfile> socialProfiles;
		Pageable pageable;
		PaginatedList socialProfilesAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		socialProfiles = this.socialProfileService.findSocialProfilesByActor(actorId, pageable);
		socialProfilesAdapted = new PaginatedListAdapter(socialProfiles, sort);

		result = new ModelAndView("socialProfile/list");
		result.addObject("socialProfiles", socialProfilesAdapted);
		result.addObject("actorId", actorId);
		result.addObject("requestURI", "socialProfile/administrator,customer,handyWorker,referee,sponsor/list.do");

		return result;

	}

	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(socialProfile);
		result = this.createEditModelAndView(socialProfile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				result = new ModelAndView("redirect:list.do?actorId=" + actor.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int socialProfileId) {
		ModelAndView result;
		Actor actor;
		SocialProfile socialProfile;

		actor = this.actorService.findPrincipal();
		socialProfile = this.socialProfileService.findOne(socialProfileId);

		try {
			this.socialProfileService.delete(socialProfile);
			result = new ModelAndView("redirect:list.do?actorId=" + actor.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
		}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String messageCode) {
		ModelAndView result;
		Actor principal;
		int actorId;

		principal = this.actorService.findPrincipal();
		actorId = principal.getId();

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);
		result.addObject("messageCode", messageCode);
		result.addObject("actorId", actorId);

		return result;

	}
}
