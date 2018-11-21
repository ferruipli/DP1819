
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.LoginService;
import security.UserAccount;
import domain.Endorsable;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	// Managed repository ------------------------------
	@Autowired
	private EndorsementRepository	endorsementRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------
	public EndorsementService() {
		super();
	}

	// Simple CRUD methods -----------------------------
	public Endorsement findOne(final int endorsementId) {
		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);

		return result;
	}

	public Collection<Endorsement> findSentEndorsements(final int senderId) {
		Collection<Endorsement> results;

		results = this.endorsementRepository.findSentEndorsements(senderId);

		return results;
	}

	public Collection<Endorsement> findReceivedEndorsements(final int recipientId) {
		Collection<Endorsement> results;

		results = this.endorsementRepository.findReceivedEndorsement(recipientId);

		return results;
	}

	public Endorsement create() {
		final Endorsement result;
		Endorsable sender;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = new Endorsement();
		sender = null;

		result.setSender(sender);

		return result;
	}
	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		Endorsement result;

		result = this.endorsementRepository.save(endorsement);

		return result;
	}

	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);

		this.endorsementRepository.delete(endorsement);
	}

	// Other business methods --------------------------

}
