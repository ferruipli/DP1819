
package services;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Complaint;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;

	// Supporting services ----------------------------------------------------

	//	@Autowired
	//	private ActorService actorService;

	@Autowired
	private UtilityService		utilityService;


	// Constructor ------------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Referee create() {
		Referee result;
		UserAccount userAccount;
		Authority authority;

		authority = new Authority();
		authority.setAuthority(Authority.REFEREE);

		userAccount = new UserAccount();
		userAccount.addAuthority(authority);

		result = new Referee();
		result.setComplaints(Collections.<Complaint> emptySet());
		result.setUserAccount(userAccount);

		return result;
	}

	public Referee save(final Referee referee) {
		Referee result;
		String password, hash;
		Md5PasswordEncoder encoder;

		this.utilityService.checkEmailActors(referee);

		encoder = new Md5PasswordEncoder();
		password = referee.getUserAccount().getPassword();
		hash = encoder.encodePassword(password, null);
		referee.getUserAccount().setPassword(hash);

		result = this.refereeRepository.save(referee);

		// COMPLT: this.actorService.createDefaultBox(result);

		return result;
	}

	public Referee findOne(final int refereeId) {
		Referee result;

		result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Referee findByPrincipal() {
		Referee result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	public Referee findByReportId(final int reportId) {
		Referee result;

		result = this.refereeRepository.findByReportId(reportId);
		Assert.notNull(result);

		return result;
	}

	public void selfAssignComplaint(final Referee referee, final Complaint complaint) {
		referee.getComplaints().add(complaint);
	}

	protected Referee findByUserAccount(final int userAccountId) {
		Referee result;

		result = this.refereeRepository.findByUserAccount(userAccountId);

		return result;
	}
}
