
package controllers.authenticated;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccountService;
import services.ActorService;
import services.EndorsableService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Endorsable;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping(value = "/actor/administrator,customer,handyWorker,referee,sponsor")
public class ActorMultiUserController extends AbstractController {

	// Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private EndorsableService	endorsableService;

	@Autowired
	private UserAccountService	userAccountService;

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
		result.addObject("curriculum", handyWorker.getCurriculum());

		return result;
	}

	// Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final String role) {
		Assert.isTrue(role.equals("customer") || role.equals("handyworker") || role.equals("sponsor"));
		final ModelAndView result;

		result = this.createActor(role);
		result.addObject("Url", "administrator,customer,handyWorker,referee,sponsor/");

		return result;
	}

	// Register

	@RequestMapping(value = "/registercustomer", method = RequestMethod.POST, params = "save")
	public ModelAndView registerCustomer(@Valid final Customer customer, final BindingResult binding, final HttpServletRequest request) {

		ModelAndView result;

		result = this.registerActor(customer, binding, request);
		result.addObject("Url", "administrator,customer,handyWorker,referee,sponsor/");

		return result;
	}

	@RequestMapping(value = "/registerhandyworker", method = RequestMethod.POST, params = "save")
	public ModelAndView registerHandyWorker(@Valid final HandyWorker handyWorker, final BindingResult binding, final HttpServletRequest request) {

		ModelAndView result;

		result = this.registerActor(handyWorker, binding, request);
		result.addObject("Url", "administrator,customer,handyWorker,referee,sponsor/");

		return result;
	}

	@RequestMapping(value = "/registersponsor", method = RequestMethod.POST, params = "save")
	public ModelAndView registerSponsor(@Valid final Sponsor sponsor, final BindingResult binding, final HttpServletRequest request) {

		ModelAndView result;

		result = this.registerActor(sponsor, binding, request);
		result.addObject("Url", "administrator,customer,handyWorker,referee,sponsor/");

		return result;
	}

	// Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		ModelAndView result;
		Actor principal;

		principal = this.actorService.findOne(actorId);

		Assert.notNull(principal);
		result = this.editModelAndView(principal);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveAdmin")
	public ModelAndView save(@Valid final Administrator actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do?actorId=" + actor.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCustomer")
	public ModelAndView save(@Valid final Customer actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do?actorId=" + actor.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHw")
	public ModelAndView save(@Valid final HandyWorker actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do?actorId=" + actor.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveReferee")
	public ModelAndView save(@Valid final Referee actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do?actorId=" + actor.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveSponsor")
	public ModelAndView save(@Valid final Sponsor actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do?actorId=" + actor.getId());
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	// Ancillary methods

	protected ModelAndView editModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.editModelAndView(actor, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Actor actor, final String messageCode) {
		ModelAndView result;
		String role;

		role = "";

		for (final Authority a : this.userAccountService.findByActor(actor).getAuthorities())
			switch (a.toString()) {
			case Authority.ADMIN:
				role = "administrator";
				break;
			case Authority.CUSTOMER:
				role = "customer";
				break;
			case Authority.HANDYWORKER:
				role = "handyworker";
				break;
			case Authority.REFEREE:
				role = "referee";
				break;
			case Authority.SPONSOR:
				role = "sponsor";
				break;
			default:
				break;
			}

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("role", role);
		result.addObject("message", messageCode);

		return result;
	}
}
