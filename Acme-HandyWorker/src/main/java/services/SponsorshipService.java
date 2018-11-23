
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository ---------------------------------------------
	@Autowired
	public SponsorshipRepository	sponsorshipRepository;


	// Supporting services -------------------------------------------

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

		Sponsorship result;

		result = this.sponsorshipRepository.save(sponsorship);

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

		this.sponsorshipRepository.delete(sponsorship);
	}
	//Other business methods-------------------------------------------

}
