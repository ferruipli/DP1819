
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Endorsable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsableServiceTest extends AbstractTest {

	// Service under testing ------------------------------------------
	@Autowired
	private EndorsableService	endorsableService;


	// Other services -------------------------------------------------

	// Tests ----------------------------------------------------------
	@Test
	public void testComputeScore_uno() {
		super.authenticate("handyworker2");

		Endorsable endorsable;

		endorsable = this.endorsableService.findByPrincipal();

		this.endorsableService.computeScore(endorsable);
		System.out.println(endorsable.getScore());
		Assert.notNull(endorsable.getScore());

		super.unauthenticate();
	}

	@Test
	public void testComputeScore_dos() {
		super.authenticate("customer1");

		Endorsable endorsable;

		endorsable = this.endorsableService.findByPrincipal();

		this.endorsableService.computeScore(endorsable);
		System.out.println(endorsable.getScore());
		Assert.notNull(endorsable.getScore());

		super.unauthenticate();
	}

	@Test
	public void testComputeScore_tres() {
		super.authenticate("customer4");

		Endorsable endorsable;

		endorsable = this.endorsableService.findByPrincipal();

		this.endorsableService.computeScore(endorsable);
		System.out.println(endorsable.getScore());
		Assert.notNull(endorsable.getScore());

		super.unauthenticate();
	}

	@Test
	public void testComputeScore_cuatro() {
		super.authenticate("handyworker3");

		Endorsable endorsable;

		endorsable = this.endorsableService.findByPrincipal();

		this.endorsableService.computeScore(endorsable);
		System.out.println(endorsable.getScore());
		Assert.notNull(endorsable.getScore());

		super.unauthenticate();
	}

}
