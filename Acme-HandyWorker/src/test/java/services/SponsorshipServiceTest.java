
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

	@Test
	public void testCreate() {
		this.authenticate("sponsor1");
		final Sponsorship sponsorship;
		sponsorship = this.sponsorshipService.create();

		Assert.notNull(sponsorship);
		super.unauthenticate();
	}
	@Test
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
	//URL NO VALIDA
	@Test(expected = javax.validation.ConstraintViolationException.class)
	public void testNegativeSave1() {
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
}
