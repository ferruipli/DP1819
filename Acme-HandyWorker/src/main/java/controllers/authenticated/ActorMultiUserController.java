
package controllers.authenticated;

import java.util.Collection;

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
import services.AdministratorService;
import services.CustomerService;
import services.EndorsableService;
import services.HandyWorkerService;
<<<<<<< HEAD
import services.RefereeService;
import services.SponsorService;
=======
>>>>>>> master
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
	private ActorService			actorService;

	@Autowired
	private EndorsableService		endorsableService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private SponsorService			sponsorService;

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
	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.GET)
	 * public ModelAndView edit() {
	 * ModelAndView result;
	 * Actor principal;
	 * 
	 * principal = this.actorService.findPrincipal();
	 * result = null;
	 * 
	 * if (principal.getUserAccount().getAuthorities().contains("ADMIN"))
	 * result = this.editAccountGet("administrator");
	 * else if (principal.getUserAccount().getAuthorities().contains("CUSTOMER"))
	 * result = this.editAccountGet("customer");
	 * else if (principal.getUserAccount().getAuthorities().contains("HANDYWORKER"))
	 * result = this.editAccountGet("handyworker");
	 * else if (principal.getUserAccount().getAuthorities().contains("REFEREE"))
	 * result = this.editAccountGet("referee");
	 * else if (principal.getUserAccount().getAuthorities().contains("SPONSOR"))
	 * result = this.editAccountGet("sponsor");
	 * 
	 * return result;
	 * 
	 * }
	 */
	/*
	 * public ModelAndView editAccountGet(final String role) {
	 * final ModelAndView result;
	 * Actor actor;
	 * 
	 * switch (role) {
	 * case "administrator":
	 * actor = this.administratorService.findByPrincipal();
	 * break;
	 * case "customer":
	 * actor = this.customerService.findByPrincipal();
	 * break;
	 * case "handyworker":
	 * actor = this.handyWorkerService.findByPrincipal();
	 * break;
	 * case "referee":
	 * actor = this.refereeService.findByPrincipal();
	 * break;
	 * case "sponsor":
	 * actor = this.sponsorService.findByPrincipal();
	 * break;
	 * default:
	 * actor = null;
	 * break;
	 * }
	 * 
	 * result = this.editModelAndView(actor, role);
	 * 
	 * return result;
	 * }
	 */
	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	 * public ModelAndView save(@Valid final Actor actor, final BindingResult binding, final HttpServletRequest request) {
	 * Assert.isTrue(request.getParameter("role").equals("administrator") || request.getParameter("role").equals("customer") || request.getParameter("role").equals("handyworker") || request.getParameter("role").equals("referee")
	 * || request.getParameter("role").equals("sponsor"));
	 * ModelAndView result;
	 * String role;
	 * 
	 * role = request.getParameter("role");
	 * 
	 * if (binding.hasErrors())
	 * result = this.editModelAndView(actor, role);
	 * else
	 * try {
	 * if (actor instanceof Administrator)
	 * this.administratorService.save((Administrator) actor);
	 * else if (actor instanceof Customer)
	 * this.customerService.save((Customer) actor);
	 * else if (actor instanceof HandyWorker)
	 * this.handyWorkerService.save((HandyWorker) actor);
	 * else if (actor instanceof Referee)
	 * this.refereeService.save((Referee) actor);
	 * else if (actor instanceof Sponsor)
	 * this.sponsorService.save((Sponsor) actor);
	 * result = new ModelAndView("redirect:display.do?actorId=" + actor.getId());
	 * } catch (final Throwable oops) {
	 * result = this.editModelAndView(actor, "actor.commit.error");
	 * }
	 * 
	 * return result;
	 * }
	 */
	// Ancillary methods
	/*
	 * protected ModelAndView editModelAndView(final Actor actor, final String role) {
	 * ModelAndView result;
	 * 
	 * result = this.editModelAndView(actor, role, null);
	 * 
	 * return result;
	 * }
	 * 
	 * 
	 * protected ModelAndView editModelAndView(final Actor actor, final String role, final String messageCode) {
	 * ModelAndView result;
	 * 
	 * result = new ModelAndView("actor/edit");
	 * result.addObject(role, actor);
	 * result.addObject("role", role);
	 * result.addObject("message", messageCode);
	 * 
	 * return result;
	 * 
	 * }
	 */

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
