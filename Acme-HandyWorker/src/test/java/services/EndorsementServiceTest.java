
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// Services under testing ---------------------------------------
	@Autowired
	private EndorsementService	endorsementService;

	// Supporting services ------------------------------------------
	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Test ---------------------------------------------------------
	@Test
	public void testPlayedRole() {
		super.authenticate("customer1");

		final int id = super.getEntityId("handyworker1");
		boolean res;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(id);

		res = this.endorsementService.playedRole(handyWorker, "HANDYWORKER");

		Assert.isTrue(res);

		super.unauthenticate();
	}

}
