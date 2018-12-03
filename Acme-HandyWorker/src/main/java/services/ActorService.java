
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.AdministratorRepository;
import repositories.CustomerRepository;
import repositories.HandyWorkerRepository;
import repositories.RefereeRepository;
import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private CustomerRepository		customerRepository;

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	@Autowired
	private RefereeRepository		refereeRepository;

	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private UtilityService			utilityService;

	@Autowired
	private BoxService				boxService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	protected Actor create(final String role) {
		Actor result;
		UserAccount userAccount;
		Authority authority;

		result = null;

		authority = new Authority();
		authority.setAuthority(role);

		userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		switch (role) {
		case Authority.ADMIN:
			result = new Administrator();
			break;
		case Authority.CUSTOMER:
			result = new Customer();
			break;
		case Authority.HANDYWORKER:
			result = new HandyWorker();
			break;
		case Authority.REFEREE:
			result = new Referee();
			break;
		case Authority.SPONSOR:
			result = new Sponsor();
			break;
		}

		result.setUserAccount(userAccount);

		return result;
	}

	protected Actor save(final Actor actor) {
		Assert.notNull(actor);
		this.utilityService.checkName(actor);
		this.utilityService.checkEmailActors(actor);

		Actor result;
		boolean isUpdating;
		String role;
		List<Authority> authorities;

		result = null;
		authorities = new ArrayList<>(actor.getUserAccount().getAuthorities());
		role = authorities.get(0).getAuthority();
		isUpdating = this.actorRepository.exists(actor.getId());
		Assert.isTrue(!isUpdating || this.isOwnerAccount(actor));

		this.definePassword(actor);

		switch (role) {
		case Authority.ADMIN:
			result = this.administratorRepository.save((Administrator) actor);
			break;
		case Authority.CUSTOMER:
			result = this.customerRepository.save((Customer) actor);
			break;
		case Authority.HANDYWORKER:
			result = this.handyWorkerRepository.save((HandyWorker) actor);
			break;
		case Authority.REFEREE:
			result = this.refereeRepository.save((Referee) actor);
			break;
		case Authority.SPONSOR:
			result = this.sponsorRepository.save((Sponsor) actor);
			break;
		}

		if (!isUpdating)
			this.boxService.createDefaultBox(result);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;
		result = this.userAccountService.findByActor(actor);

		return result;
	}

	public Actor findPrincipal() {
		Actor result;
		int userAccountId;

		userAccountId = LoginService.getPrincipal().getId();

		result = this.actorRepository.findActorByUseraccount(userAccountId);
		Assert.notNull(result);

		return result;
	}

	public Actor findActorByUseraccount(final int id) {
		Actor res;

		res = this.findActorByUseraccount(id);

		return res;
	}

	public Actor findSystem() {
		Actor result;

		result = this.actorRepository.findSystem();
		Assert.notNull(result);

		return result;
	}

	public void changeBanner(final Actor actor) {
		Assert.notNull(actor);

		final UserAccount userAccount;
		boolean isBanned;

		userAccount = actor.getUserAccount();
		isBanned = userAccount.getIsBanned();

		userAccount.setIsBanned(!isBanned);

		this.userAccountService.save(userAccount);
	}

	public void definePassword(final Actor actor) {
		Md5PasswordEncoder encoder;
		String password, hash;

		encoder = new Md5PasswordEncoder();
		password = actor.getUserAccount().getPassword();
		hash = encoder.encodePassword(password, null);

		actor.getUserAccount().setPassword(hash);
	}

	public void isSuspicious(final Actor actor) {
		actor.setIsSuspicious(true);
		this.actorRepository.save(actor);
	}

	private boolean isOwnerAccount(final Actor actor) {
		int principalId;

		principalId = LoginService.getPrincipal().getId();
		return principalId == actor.getUserAccount().getId();
	}
}
