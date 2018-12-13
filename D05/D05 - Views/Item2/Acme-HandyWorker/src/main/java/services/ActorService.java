
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository		actorRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private BoxService			boxService;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	protected UserAccount createUserAccount(final String role) {
		UserAccount userAccount;
		Authority authority;

		authority = new Authority();
		authority.setAuthority(role);

		userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		return userAccount;
	}

	protected Actor save(final Actor actor) {
		Assert.notNull(actor);
		this.utilityService.checkUsername(actor);
		this.utilityService.checkEmailActors(actor);

		Actor result;
		boolean isUpdating;

		isUpdating = this.actorRepository.exists(actor.getId());
		Assert.isTrue(!isUpdating || this.isOwnerAccount(actor));

		result = this.actorRepository.save(actor);

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

	public void changeBan(final Actor actor) {
		Assert.notNull(actor);

		final UserAccount userAccount;
		boolean isBanned;

		userAccount = actor.getUserAccount();
		isBanned = userAccount.getIsBanned();

		userAccount.setIsBanned(!isBanned);
	}

	public void markAsSuspicious(final Actor actor) {
		actor.setIsSuspicious(true);
	}

	public Collection<Actor> findAllSuspicious() {
		Collection<Actor> result;

		result = this.actorRepository.findAllSuspicious();
		Assert.notNull(result);

		return result;
	}

	private boolean isOwnerAccount(final Actor actor) {
		int principalId;

		principalId = LoginService.getPrincipal().getId();
		return principalId == actor.getUserAccount().getId();
	}
}
