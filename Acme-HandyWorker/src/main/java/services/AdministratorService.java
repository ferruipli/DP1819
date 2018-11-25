
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository ----------------------------------
	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting repositories -----------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private UtilityService			utilityService;


	// Constructors ----------------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods ---------------------------------
	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;
	}

	public Administrator create() {
		Administrator result;
		Authority role;
		List<Authority> authorities;
		UserAccount userAccount;

		role = new Authority();
		role.setAuthority(Authority.ADMIN);

		authorities = new ArrayList<Authority>();
		authorities.add(role);

		userAccount = new UserAccount();
		userAccount.setAuthorities(authorities);

		result = new Administrator();
		result.setUserAccount(userAccount);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		this.utilityService.checkEmailAdministrator(administrator);

		final Administrator result;
		Md5PasswordEncoder encoder;
		String password, hash;

		encoder = new Md5PasswordEncoder();
		password = administrator.getUserAccount().getPassword();
		hash = encoder.encodePassword(password, null);
		administrator.getUserAccount().setPassword(hash);

		if (administrator.getId() == 0)
			result = this.administratorRepository.save(administrator);
		//TODO:this.actorService.initializeSystemBox();
		else {
			this.checkByPrincipal(administrator);

			result = this.administratorRepository.save(administrator);
		}

		return result;
	}

	// Other business methods ------------------------------
	public void checkByPrincipal(final Administrator administrator) {
		Administrator principal;

		principal = this.findByPrincipal();

		Assert.isTrue(administrator.equals(principal));
	}

	public Administrator findByPrincipal() {
		UserAccount userAccount;
		Administrator result;

		userAccount = LoginService.getPrincipal();
		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	private Administrator findByUserAccount(final int userAccountId) {
		Administrator result;

		result = this.administratorRepository.findByUserAccount(userAccountId);

		return result;
	}

}
