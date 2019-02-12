
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
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;


	// Test ------------------------------------------------

	@Test(expected = IllegalArgumentException.class)
	public void testSave() {
		this.authenticate("sponsor1");
		final Sponsorship sponsorship;
		final Sponsorship sponsorshipSaved;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));

		sponsorship.setBanner("http://baner.com");
		sponsorshipSaved = this.sponsorshipService.save(sponsorship);

		Assert.notNull(sponsorshipSaved);
		super.unauthenticate();
	}

	//Modificar un finder que no el no es el propietario
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSave() {
		this.authenticate("sponsor1");
		final Sponsorship sponsorship;
		final Sponsorship sponsorshipSaved;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship3"));

		sponsorship.setBanner("http://baner.com");
		sponsorshipSaved = this.sponsorshipService.save(sponsorship);

		Assert.notNull(sponsorshipSaved);
		super.unauthenticate();
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSave1() {
		this.authenticate("sponsor1");
		final Sponsorship sponsorship;
		final Sponsorship sponsorshipSaved;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship3"));

		sponsorship.setBanner("/baner.com");
		sponsorshipSaved = this.sponsorshipService.save(sponsorship);

		Assert.notNull(sponsorshipSaved);
		super.unauthenticate();
	}
	@Test
	public void testDelete() {

		this.authenticate("sponsor1");
		final Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		this.sponsorshipService.delete(sponsorship);
		super.unauthenticate();

	}

	@Test
	public void testFindAll() {
		Collection<Sponsorship> sponsorships;
		sponsorships = this.sponsorshipService.findAll();
		Assert.notEmpty(sponsorships);
		Assert.notNull(sponsorships);

	}

	@Test
	public void testFindOne() {
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship1"));
		Assert.notNull(sponsorship);

	}

	/*
	 * Credit card which number is not correct
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeCreckCreditCard() {
		super.authenticate("sponsor1");
		final Sponsorship sponsorship;
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setBrandName("maria");
		creditCard.setCvvCode(123);
		creditCard.setExpirationMonth("08");
		creditCard.setHolderName("maria");
		creditCard.setExpirationYear("22");
		creditCard.setNumber("670209");

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship3"));
		this.sponsorshipService.addCreditCard(sponsorship, creditCard);
		Assert.isTrue(sponsorship.getCreditCard().equals(creditCard));
		super.unauthenticate();
	}

	@Test
	public void testCreckCreditCard() {
		super.authenticate("sponsor1");
		final Sponsorship sponsorship;
		CreditCard creditCard;

		creditCard = new CreditCard();
		creditCard.setBrandName("maria");
		creditCard.setCvvCode(123);
		creditCard.setExpirationMonth("08");
		creditCard.setHolderName("maria");
		creditCard.setExpirationYear("22");
		creditCard.setNumber("3073930266269350");

		sponsorship = this.sponsorshipService.findOne(super.getEntityId("sponsorship3"));
		this.sponsorshipService.addCreditCard(sponsorship, creditCard);
		Assert.isTrue(sponsorship.getCreditCard().equals(creditCard));
		super.unauthenticate();
	}
}
