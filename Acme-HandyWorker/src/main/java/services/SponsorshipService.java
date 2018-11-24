
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


	//Constructor ----------------------------------------------------
	public SponsorshipService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Sponsorship create() {
		Sponsorship result;
		CreditCard creditCard;

		result = new Sponsorship();
		creditCard = new CreditCard();

		result.setCreditCard(creditCard);
		Assert.notNull(creditCard);

		return result;
	}
	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsor principal;
		Sponsor sponsorOwner;
		Sponsorship result;

		principal = this.sponsorService.findByPrincipal();

		if (sponsorship.getId() == 0) {
			result = this.sponsorshipRepository.save(sponsorship);
			this.addSponsorshipToSponsor(principal, result);
		} else {
			sponsorOwner = this.sponsorService.findSponsorBySponsorshipId(sponsorship.getId());
			Assert.isTrue(principal.equals(sponsorOwner));
			result = this.sponsorshipRepository.save(sponsorship);
		}

		return result;
	}

	public Sponsorship findOne(final int idSponsorship) {
		Sponsorship result;

		Assert.isTrue(idSponsorship != 0);

		result = this.sponsorshipRepository.findOne(idSponsorship);

		return result;
	}
	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;

		result = this.sponsorshipRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Sponsorship sponsorship) {
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.notNull(sponsorship);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));

		this.tutorialService.removeSponsorShipToTutorial(sponsorship);
		this.sponsorshipRepository.delete(sponsorship);
	}
	//Other business methods-------------------------------------------
	protected void addSponsorshipToSponsor(final Sponsor sponsor, final Sponsorship sponsorship) {
		Collection<Sponsorship> sponsorships;

		sponsorships = sponsor.getSponsorships();
		Assert.isTrue(!(sponsorships.contains(sponsorship)));
		sponsorships.add(sponsorship);
		sponsor.setSponsorships(sponsorships);
		Assert.isTrue(sponsorships.contains(sponsorship));

	}
	public void addCreditCardToSponsorship(final Sponsorship sponsorship, final CreditCard creditCard) {
		Assert.notNull(creditCard);
		sponsorship.setCreditCard(creditCard);
		Assert.isTrue(sponsorship.getCreditCard().equals(creditCard));
	}

}
