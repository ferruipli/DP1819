
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
		CreditCard result;

		Assert.isTrue(idCreditCard != 0);

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

		creditCardinSponsorship = this.findSponsorshipByCreditCard(creditCard.getId());
		creditCardinApplication = this.findApplicationByCreditCard(creditCard.getId());

		Assert.isTrue(creditCardinApplication.isEmpty());
		Assert.isTrue(creditCardinSponsorship.isEmpty());

		this.creditCardRepository.delete(creditCard);
	}
	//Other business methods-------------------------------------------

	private Collection<Sponsorship> findSponsorshipByCreditCard(final int id) {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.creditCardRepository.findSponsorshipByCreditCard(id);

		return sponsorships;
	}
	private Collection<Application> findApplicationByCreditCard(final int id) {
		Collection<Application> sponsorships;

		sponsorships = this.creditCardRepository.findApplicationByCreditCard(id);

		return sponsorships;
	}

}
