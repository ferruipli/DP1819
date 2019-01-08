/*
 * AbstractController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.AdministratorService;
import services.CustomerService;
import services.CustomisationService;
import services.HandyWorkerService;
import services.RefereeService;
import services.SponsorService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Customisation;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Controller
public class AbstractController {

	// Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CustomisationService	customisationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private UserAccountService		userAccountService;


	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	protected Pageable newFixedPageable(final int page, final String dir, final String sort) {
		Pageable result;

		if (sort != null && !sort.isEmpty())
			result = new PageRequest(page - 1, 5, Sort.Direction.fromString(dir), sort);
		else
			result = new PageRequest(page - 1, 5);

		return result;
	}

	public ModelAndView createActor(final String role) {
		ModelAndView result;
		Actor actor;

		switch (role) {
		case "administrator":
			actor = this.administratorService.create();
			result = this.createModelAndView(actor, role);
			break;
		case "customer":
			actor = this.customerService.create();
			result = this.createModelAndView(actor, role);
			break;
		case "handyworker":
			actor = this.handyWorkerService.create();
			result = this.createModelAndView(actor, role);
			break;
		case "referee":
			actor = this.refereeService.create();
			result = this.createModelAndView(actor, role);
			break;
		case "sponsor":
			actor = this.sponsorService.create();
			result = this.createModelAndView(actor, role);
			break;
		default:
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("message", "actor.commit.error");
			break;
		}

		return result;

	}

	public ModelAndView registerActor(final Actor actor, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		//final String hashedPassword;
		String username, password, role, confirmPassword;
		final Administrator administrator;
		final Customer customer;
		final HandyWorker handyWorker;
		final Referee referee;
		final Sponsor sponsor;
		UserAccount userAccount;

		username = request.getParameter("username");
		password = request.getParameter("password");
		confirmPassword = request.getParameter("confirmPassword");
		role = request.getParameter("role");

		if (binding.hasErrors())
			result = this.createModelAndView(actor, role);
		else if (!confirmPassword.equals(password))
			result = this.createModelAndView(actor, role, "actor.password.missmatch");
		else if (this.userAccountService.existUsername(username))
			result = this.createModelAndView(actor, role, "actor.username.used");
		else if (this.actorService.existEmail(actor.getEmail()))
			result = this.createModelAndView(actor, role, "actor.email.used");
		else if (password.length() < 5 || password.length() > 32)
			result = this.createModelAndView(actor, role, "actor.password.size");
		else if (username.length() < 5 || username.length() > 32)
			result = this.createModelAndView(actor, role, "actor.username.size");
		else
			try {
				//hashedPassword = this.hashPassword(password);
				//this.userAccountService.setLogin(actor.getUserAccount(), username, hashedPassword);
				this.userAccountService.setLogin(actor.getUserAccount(), username, password);
				userAccount = actor.getUserAccount();

				userAccount.setIsBanned(false);

				switch (role) {
				case "administrator":
					administrator = (Administrator) actor;
					this.administratorService.save(administrator);
					break;
				case "customer":
					customer = (Customer) actor;
					this.customerService.save(customer);
					break;
				case "handyworker":
					handyWorker = (HandyWorker) actor;
					this.handyWorkerService.save(handyWorker);
					break;
				case "referee":
					referee = (Referee) actor;
					this.refereeService.save(referee);
					break;
				case "sponsor":
					sponsor = (Sponsor) actor;
					this.sponsorService.save(sponsor);
					break;
				default:
					throw new Throwable();
				}

				result = new ModelAndView("redirect:/welcome/index.do");
				result.addObject("message", "actor.registration.successful");
			} catch (final Throwable oops) {
				result = this.createModelAndView(actor, role, "actor.commit.error");
			}

		return result;
	}

	public String hashPassword(final String password) {
		String result;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();

		if (!"".equals(password) && password != null)
			result = encoder.encodePassword(password, null);
		else
			result = null;

		return result;
	}

	// Protected ancillary methods -------------------------------------------
	protected void setBannerHeader(final ModelAndView modelAndView) {
		Customisation customisation;
		String banner;

		customisation = this.customisationService.find();
		banner = customisation.getBanner();

		modelAndView.addObject("banner", banner);
	}

	protected ModelAndView createModelAndView(final Actor actor, final String role) {
		ModelAndView result;

		result = this.createModelAndView(actor, role, null);

		return result;
	}

	protected ModelAndView createModelAndView(final Actor actor, final String role, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/singup");
		result.addObject("role", role);
		result.addObject(role, actor);

		result.addObject("message", messageCode);

		return result;
	}

}
