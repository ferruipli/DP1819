
package services;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
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

	// Constructor ------------------------------------------------------------

	public RefereeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Referee create() {
		Referee result;

		result = new Referee();
		result.setComplaints(Collections.<Complaint> emptySet());

		return result;
	}

	public Referee save(final Referee referee) {
		Referee result;

		Assert.isTrue(referee.getEmail().matches("[A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+ | [\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@[a-zA-Z0-9.-]+[\\>]\\n | [A-Za-z_.]+[\\w]+@ |  [\\w\\s]+[\\<][A-Za-z_.]+[\\w]+@+[\\>]"));
		result = this.refereeRepository.save(referee);
		// COMPLT: this.actorService.createDefaultBox(referee);

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

	private Referee findByUserAccount(final int userAccountId) {
		Referee result;

		result = this.refereeRepository.findByUserAccount(userAccountId);

		return result;
	}

	public void selfAssignComplaint(final Referee referee, final Complaint complaint) {
		referee.getComplaints().add(complaint);
	}
}
