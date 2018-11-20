
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

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

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);

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
		Assert.isTrue(creditCard.getId() != 0);
		Assert.notNull(creditCard);
		Assert.isTrue(this.creditCardRepository.exists(creditCard.getId()));

		this.creditCardRepository.delete(creditCard);
	}
	//Other business methods-------------------------------------------

}
