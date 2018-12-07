
package services;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ActorService		actorService;


	// Constructor ------------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Referee create() {
		Referee result;

		result = new Referee();
		result.setUserAccount(this.actorService.createUserAccount(Authority.REFEREE));
		result.setComplaints(Collections.<Complaint> emptySet());

		return result;
	}

	public Referee save(final Referee referee) {
		Referee result;

		result = (Referee) this.actorService.save(referee);

		return result;
	}

	public Referee findOne(final int refereeId) {
		Referee result;

		result = this.refereeRepository.findOne(refereeId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public void selfAssignComplaint(final Referee referee, final Complaint complaint) {
		referee.getComplaints().add(complaint);
	}

	public Referee findByPrincipal() {
		Referee result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	protected Referee findByReportId(final int reportId) {
		Referee result;

		result = this.refereeRepository.findByReportId(reportId);
		Assert.notNull(result);

		return result;
	}

	protected Referee findByUserAccount(final int userAccountId) {
		Referee result;

		result = this.refereeRepository.findByUserAccount(userAccountId);

		return result;
	}
}
