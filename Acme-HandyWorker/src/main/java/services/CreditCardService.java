
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.Application;
import domain.CreditCard;
import domain.Sponsorship;

@Service
@Transactional
public class CreditCardService {

	// Managed repository ---------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Supporting services -------------------------------------------
	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private SponsorshipService		sponsorshipService;


	//Constructor ----------------------------------------------------
	public CreditCardService() {
		super();
	}

	//Simple CRUD methods -------------------------------------------

	public CreditCard create() {
		CreditCard result;

		result = new CreditCard();

		return result;
	}

	/*
	 * No se va a poder editaar una creditCard, no lo contempla los requisitos
	 */
	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() == 0);

		CreditCard result;

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public CreditCard findOne(final int idCreditCard) {
		Assert.isTrue(idCreditCard != 0);

		CreditCard result;

		result = this.creditCardRepository.findOne(idCreditCard);

		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));
		Collection<Sponsorship> creditCardinSponsorship;
		Collection<Application> creditCardinApplication;

		creditCardinSponsorship = this.sponsorshipService.findSponsorshipByCreditCard(creditCard.getId());
		creditCardinApplication = this.applicationService.findApplicationByCreditCard(creditCard.getId());

		Assert.isTrue(creditCardinApplication.isEmpty());
		Assert.isTrue(creditCardinSponsorship.isEmpty());

		this.creditCardRepository.delete(creditCard);
	}

	//Other business methods-------------------------------------------

}
