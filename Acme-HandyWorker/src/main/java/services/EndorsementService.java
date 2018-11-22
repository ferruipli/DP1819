
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import security.UserAccount;
import domain.Customer;
import domain.Endorsable;
import domain.Endorsement;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	// Managed repository ------------------------------
	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services -----------------------------
	@Autowired
	private CustomerService			customerService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private EndorsableService		endorsableService;


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

	public Endorsement create() {
		Endorsement result;
		Endorsable sender;

		sender = this.endorsableService.findByPrincipal();

		result = new Endorsement();
		result.setSender(sender);

		return result;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		boolean is_role;
		Date moment;
		Endorsement result;
		Endorsable sender, principal;
		final Collection<Customer> customers;
		final Collection<HandyWorker> handyWorkers;

		principal = this.endorsableService.findByPrincipal();
		sender = endorsement.getSender();

		Assert.isTrue(sender.equals(principal));

		is_role = this.playedRole(sender, "CUSTOMER");

		if (is_role) {
			handyWorkers = this.handyWorkerService.findEndorsableHandyWorkers(sender.getId());

			Assert.notNull(handyWorkers);
			Assert.isTrue(handyWorkers.size() > 0);
			Assert.isTrue(handyWorkers.contains(endorsement.getRecipient()));
		} else {
			customers = this.customerService.findEndorsableCustomers(sender.getId());

			Assert.notNull(customers);
			Assert.isTrue(customers.size() > 0);
			Assert.isTrue(customers.contains(endorsement.getRecipient()));
		}

		moment = new Date(System.currentTimeMillis() - 1);

		endorsement.setMoment(moment);
		result = this.endorsementRepository.save(endorsement);

		return result;
	}

	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);

		Endorsable principal;

		principal = this.endorsableService.findByPrincipal();

		Assert.isTrue(principal.equals(endorsement.getSender()));

		this.endorsementRepository.delete(endorsement);
	}

	// Other business methods --------------------------

	// private methods ---------------------------------
	public Collection<Endorsement> findSentEndorsements() {
		Collection<Endorsement> results;
		Endorsable principal;

		principal = this.endorsableService.findByPrincipal();

		results = this.endorsementRepository.findSentEndorsements(principal.getId());

		return results;
	}

	public Collection<Endorsement> findReceivedEndorsements() {
		Collection<Endorsement> results;
		Endorsable principal;

		principal = this.endorsableService.findByPrincipal();

		results = this.endorsementRepository.findReceivedEndorsement(principal.getId());

		return results;
	}

	public boolean playedRole(final Endorsable endorsable, final String role) {
		UserAccount userAccount;
		Authority authority;
		List<Authority> authorities;
		boolean result;

		userAccount = endorsable.getUserAccount();
		authorities = new ArrayList<Authority>(userAccount.getAuthorities());
		authority = authorities.get(0);

		result = authority.getAuthority().equals(role);

		return result;
	}

}
