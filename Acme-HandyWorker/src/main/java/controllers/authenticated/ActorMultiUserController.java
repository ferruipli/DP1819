
package controllers.authenticated;

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
import controllers.ActorAbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
@RequestMapping(value = "/actor/administrator,customer,handyWorker,referee,sponsor")
public class ActorMultiUserController extends ActorAbstractController {

	// Services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;


	// Constructor

	public ActorMultiUserController() {
		super();
	}

	// Display

	@Override
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) final Integer actorId) {
		ModelAndView result;

		result = super.display(actorId);

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
	public ModelAndView save(@Valid final Administrator actor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		String newUsername, newPassword, confirmPassword, email;

		confirmPassword = request.getParameter("confirmPassword");
		newPassword = request.getParameter("newPassword");
		newUsername = request.getParameter("newUsername");
		email = request.getParameter("email");

		result = new ModelAndView();

		if (!newUsername.isEmpty() && !newPassword.isEmpty())
			if (this.userAccountService.existUsername(newUsername))
				this.editModelAndView(actor, "actor.username.used");
			else if (newPassword.length() < 5 || newPassword.length() > 32)
				this.editModelAndView(actor, "actor.password.size");
			else if (!confirmPassword.equals(newPassword))
				this.editModelAndView(actor, "actor.password.missmatch");
			else
				this.userAccountService.setLogin(actor.getUserAccount(), newUsername, newPassword);

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else if (!actor.getEmail().equals(email)) {
			if (this.actorService.existEmail(email))
				result = this.editModelAndView(actor, "actor.email.used");
		} else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCustomer")
	public ModelAndView save(@Valid final Customer actor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		String newUsername, newPassword, confirmPassword, email;

		confirmPassword = request.getParameter("confirmPassword");
		newPassword = request.getParameter("newPassword");
		newUsername = request.getParameter("newUsername");
		email = request.getParameter("email");

		result = new ModelAndView();

		if (!newUsername.isEmpty() && !newPassword.isEmpty())
			if (this.userAccountService.existUsername(newUsername))
				this.editModelAndView(actor, "actor.username.used");
			else if (newPassword.length() < 5 || newPassword.length() > 32)
				this.editModelAndView(actor, "actor.password.size");
			else if (!confirmPassword.equals(newPassword))
				this.editModelAndView(actor, "actor.password.missmatch");
			else
				this.userAccountService.setLogin(actor.getUserAccount(), newUsername, newPassword);

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else if (!actor.getEmail().equals(email)) {
			if (this.actorService.existEmail(email))
				result = this.editModelAndView(actor, "actor.email.used");
		} else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveHw")
	public ModelAndView save(@Valid final HandyWorker actor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		String newUsername, newPassword, confirmPassword, email;

		confirmPassword = request.getParameter("confirmPassword");
		newPassword = request.getParameter("newPassword");
		newUsername = request.getParameter("newUsername");
		email = request.getParameter("email");

		result = new ModelAndView();

		if (!newUsername.isEmpty() && !newPassword.isEmpty())
			if (this.userAccountService.existUsername(newUsername))
				this.editModelAndView(actor, "actor.username.used");
			else if (newPassword.length() < 5 || newPassword.length() > 32)
				this.editModelAndView(actor, "actor.password.size");
			else if (!confirmPassword.equals(newPassword))
				this.editModelAndView(actor, "actor.password.missmatch");
			else
				this.userAccountService.setLogin(actor.getUserAccount(), newUsername, newPassword);

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else if (!actor.getEmail().equals(email)) {
			if (this.actorService.existEmail(email))
				result = this.editModelAndView(actor, "actor.email.used");
		} else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveReferee")
	public ModelAndView save(@Valid final Referee actor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		String newUsername, newPassword, confirmPassword, email;

		confirmPassword = request.getParameter("confirmPassword");
		newPassword = request.getParameter("newPassword");
		newUsername = request.getParameter("newUsername");
		email = request.getParameter("email");

		result = new ModelAndView();

		if (!newUsername.isEmpty() && !newPassword.isEmpty())
			if (this.userAccountService.existUsername(newUsername))
				this.editModelAndView(actor, "actor.username.used");
			else if (newPassword.length() < 5 || newPassword.length() > 32)
				this.editModelAndView(actor, "actor.password.size");
			else if (!confirmPassword.equals(newPassword))
				this.editModelAndView(actor, "actor.password.missmatch");
			else
				this.userAccountService.setLogin(actor.getUserAccount(), newUsername, newPassword);

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else if (!actor.getEmail().equals(email)) {
			if (this.actorService.existEmail(email))
				result = this.editModelAndView(actor, "actor.email.used");
		} else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(actor, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveSponsor")
	public ModelAndView save(@Valid final Sponsor actor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;

		String newUsername, newPassword, confirmPassword, email;

		confirmPassword = request.getParameter("confirmPassword");
		newPassword = request.getParameter("newPassword");
		newUsername = request.getParameter("newUsername");
		email = request.getParameter("email");

		result = new ModelAndView();

		if (!newUsername.isEmpty() && !newPassword.isEmpty())
			if (this.userAccountService.existUsername(newUsername))
				this.editModelAndView(actor, "actor.username.used");
			else if (newPassword.length() < 5 || newPassword.length() > 32)
				this.editModelAndView(actor, "actor.password.size");
			else if (!confirmPassword.equals(newPassword))
				this.editModelAndView(actor, "actor.password.missmatch");
			else
				this.userAccountService.setLogin(actor.getUserAccount(), newUsername, newPassword);

		if (binding.hasErrors())
			result = this.editModelAndView(actor);
		else if (!actor.getEmail().equals(email)) {
			if (this.actorService.existEmail(email))
				result = this.editModelAndView(actor, "actor.email.used");
		} else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:display.do");
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
		result.addObject("messageCode", messageCode);

		return result;
	}
}
