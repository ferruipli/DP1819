
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Autowired
	private UtilityService			utilityService;


	// Constructors ------------------------------------
	public EndorsementService() {
		super();
	}

	// Simple CRUD methods -----------------------------
	public Endorsement findOne(final int endorsementId) {
		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);
		Assert.notNull(result);

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
		this.checkByPrincipal(endorsement);

		boolean is_role;
		Date moment;
		Endorsement result;
		Endorsable sender, recipient;
		final Collection<Customer> customers;
		final Collection<HandyWorker> handyWorkers;

		sender = endorsement.getSender();
		recipient = endorsement.getRecipient();

		is_role = this.playedRole(sender, "CUSTOMER");

		if (is_role) {
			handyWorkers = this.handyWorkerService.findEndorsableHandyWorkers(sender.getId());

			Assert.isTrue(handyWorkers != null && handyWorkers.size() > 0);
			Assert.isTrue(handyWorkers.contains(recipient));
		} else {
			customers = this.customerService.findEndorsableCustomers(sender.getId());

			Assert.isTrue(customers != null && customers.size() > 0);
			Assert.isTrue(customers.contains(recipient));
		}

		moment = this.utilityService.current_moment();
		endorsement.setMoment(moment);

		result = this.endorsementRepository.save(endorsement);

		if (endorsement.getId() != 0 && recipient.getScore() != null)
			// Si un endorsable modifica el atributo endorsement::comments, entonces
			// volver a recalcular el atributo Endorsable::score del recipient si
			// anteriormente un administrator le calculo su valor
			this.endorsableService.computeScore(recipient);

		return result;
	}

	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		Assert.isTrue(endorsement.getId() != 0);
		this.checkByPrincipal(endorsement);

		Endorsable recipient;
		recipient = endorsement.getRecipient();

		this.endorsementRepository.delete(endorsement);

		if (recipient.getScore() != null)
			this.endorsableService.computeScore(recipient);
	}

	// Other business methods --------------------------
	public void checkByPrincipal(final Endorsement endorsement) {
		Endorsable principal;

		principal = this.endorsableService.findByPrincipal();

		Assert.isTrue(principal.equals(endorsement.getSender()));
	}

	public Page<Endorsement> findSentEndorsements() {
		Page<Endorsement> results;
		Endorsable principal;
		Pageable pageable;

		principal = this.endorsableService.findByPrincipal();
		pageable = new PageRequest(0, 5);

		results = this.endorsementRepository.findSentEndorsements(principal.getId(), pageable);

		return results;
	}

	public Page<Endorsement> findReceivedEndorsements() {
		Page<Endorsement> results;
		Endorsable principal;
		Pageable pageable;

		principal = this.endorsableService.findByPrincipal();

		pageable = new PageRequest(0, 5);

		results = this.endorsementRepository.findReceivedEndorsement(principal.getId(), pageable);

		return results;
	}

	// private methods ---------------------------------
	private boolean playedRole(final Endorsable endorsable, final String role) {
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
