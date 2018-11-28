
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

	/*
	 * Se va a poder modificar ya que como sponsorship siempre tiene que tener creditCard no se puede borrar
	 * En el caso que quiera cambiar de tarjeta de credito se editará
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
	/*
	 * No se puede borrar un creditCard porque esta relacionado con Application y sponsorship
	 * Sponsorship siempre va a tener un creditcard, por lo tanto no se va a poder borrar
	 * Y application si tiene creditcard es porque esta aceptada y en ese caso es obligatorio la creditcard
	 */

	//Other business methods-------------------------------------------

}
