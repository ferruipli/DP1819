
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
	public void testComputeScoreProcess() {
		super.authenticate("admin1");
		Endorsable e_handyWorker2, e_handyWorker3, e_customer1, e_customer4;
		int id_hw2, id_hw3, id_c1, id_c4;

		id_hw2 = super.getEntityId("handyworker2");
		id_hw3 = super.getEntityId("handyworker3");
		id_c1 = super.getEntityId("customer1");
		id_c4 = super.getEntityId("customer4");

		this.endorsableService.computingScoreProcess();

		e_handyWorker2 = this.endorsableService.findOne(id_hw2);
		e_handyWorker3 = this.endorsableService.findOne(id_hw3);
		e_customer1 = this.endorsableService.findOne(id_c1);
		e_customer4 = this.endorsableService.findOne(id_c4);

		Assert.notNull(e_handyWorker2.getScore());
		Assert.notNull(e_handyWorker3.getScore());
		Assert.notNull(e_customer1.getScore());
		Assert.notNull(e_customer4.getScore());

		super.unauthenticate();
	}

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
