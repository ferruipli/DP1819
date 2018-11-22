
package services;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import domain.Complaint;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RefereeRepository	refereeRepository;


	// Supporting services ----------------------------------------------------

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

		result = this.refereeRepository.save(referee);

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
}
