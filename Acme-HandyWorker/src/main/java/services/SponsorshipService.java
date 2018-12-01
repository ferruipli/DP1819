
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository ---------------------------------------------
	@Autowired
	public SponsorshipRepository	sponsorshipRepository;

	// Supporting services -------------------------------------------
	@Autowired
	public SponsorService			sponsorService;

	@Autowired
	public ActorService				actorService;

	@Autowired
	public TutorialService			tutorialService;

	@Autowired
	public CreditCardService		creditCardService;


	//Constructor ----------------------------------------------------
	public SponsorshipService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------
	public Sponsorship create() {
		Sponsorship result;
		CreditCard creditCard;

		creditCard = this.creditCardService.create();

		result = new Sponsorship();
		result.setCreditCard(creditCard);

		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);

		Sponsor principal;
		Sponsorship result;

		principal = this.sponsorService.findByPrincipal();

		if (sponsorship.getId() == 0) {
			result = this.sponsorshipRepository.save(sponsorship);
			this.addSponsorshipToSponsor(principal, result);
		} else {
			this.checkByPrincipal(sponsorship);
			result = this.sponsorshipRepository.save(sponsorship);
		}

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		Sponsorship result;

		result = this.sponsorshipRepository.findOne(sponsorshipId);

		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		this.checkByPrincipal(sponsorship);

		Sponsor principal;
		principal = this.sponsorService.findByPrincipal();

		this.removeSponsorshipToSponsor(principal, sponsorship);
		this.removeSponsorShipToTutorial(sponsorship);

		this.sponsorshipRepository.delete(sponsorship);
	}

	//Other business methods-------------------------------------------
	protected void checkByPrincipal(final Sponsorship sponsorship) {
		final Sponsor principal;
		Sponsor sponsorOwner;

		principal = this.sponsorService.findByPrincipal();
		sponsorOwner = this.sponsorService.findSponsorBySponsorshipId(sponsorship.getId());

		Assert.isTrue(principal.equals(sponsorOwner));
	}

	protected Collection<Sponsorship> findSponsorshipByCreditCard(final int id) {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipRepository.findSponsorshipByCreditCard(id);

		return sponsorships;
	}

	protected void addSponsorshipToSponsor(final Sponsor sponsor, final Sponsorship sponsorship) {
		sponsor.getSponsorships().add(sponsorship);
	}

	protected void removeSponsorshipToSponsor(final Sponsor sponsor, final Sponsorship sponsorship) {
		sponsor.getSponsorships().remove(sponsorship);
	}

	public void addCreditCardToSponsorship(final Sponsorship sponsorship, final CreditCard creditCard) {
		Assert.notNull(creditCard);

		sponsorship.setCreditCard(creditCard);
	}

	public void removeSponsorShipToTutorial(final Sponsorship sponsorship) {
		Tutorial tutorial;
		Collection<Sponsorship> sponsorships;

		tutorial = this.tutorialService.findTutorialBySponsorship(sponsorship);
		sponsorships = tutorial.getSponsorShips();
		sponsorships.remove(sponsorship);

		tutorial.setSponsorShips(sponsorships);
	}

	public void addSponsorShipToTutorial(final Sponsorship sponsorship) {
		Tutorial tutorial;
		Collection<Sponsorship> sponsorships;

		tutorial = this.tutorialService.findTutorialBySponsorship(sponsorship);
		sponsorships = tutorial.getSponsorShips();
		sponsorships.add(sponsorship);

		tutorial.setSponsorShips(sponsorships);
	}

}
