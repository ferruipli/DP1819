
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsableRepository;
import security.LoginService;
import security.UserAccount;
import domain.Endorsable;

@Service
@Transactional
public class EndorsableService {

	// Managed repository ------------------------------
	@Autowired
	private EndorsableRepository	endorsableRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public EndorsableService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	// Other business methods --------------------------

	public Endorsable findByPrincipal() {
		UserAccount userAccount;
		Endorsable result;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	private Endorsable findByUserAccount(final int userAccountId) {
		Endorsable result;

		result = this.endorsableRepository.findByUserAccount(userAccountId);

		return result;
	}

}
