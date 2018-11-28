
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private CreditCardService	creditCardService;


	// Test ------------------------------------------------
	@Test
	public void testCreate() {
		final CreditCard creditCard;
		creditCard = this.creditCardService.create();
		Assert.notNull(creditCard);
	}
	@Test
	public void testSave() {
		final CreditCard creditCard;
		final CreditCard creditCardSaved;

		creditCard = this.creditCardService.create();
		creditCardSaved = this.creditCardService.save(creditCard);
		Assert.notNull(creditCardSaved);
	}

	@Test
	public void testFindAll() {
		Collection<CreditCard> creditCards;
		creditCards = this.creditCardService.findAll();
		Assert.notEmpty(creditCards);
		Assert.notNull(creditCards);

	}

	@Test
	public void testFindOne() {
		CreditCard creditCard;

		creditCard = this.creditCardService.findOne(super.getEntityId("creditCard1"));
		Assert.notNull(creditCard);

	}
}
