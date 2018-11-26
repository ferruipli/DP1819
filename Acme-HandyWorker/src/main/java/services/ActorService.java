
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
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


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

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

	public Actor findActorByName(final String nameActor) {
		Actor result;
		result = this.actorRepository.findActorByName(nameActor);
		Assert.notNull(result);
		return result;
	}

	public void isBanner(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		userAccount.setIsBanned(true);
		this.userAccountService.save(userAccount);
	}

	public void notBanner(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		userAccount.setIsBanned(false);
		this.userAccountService.save(userAccount);
	}
}
