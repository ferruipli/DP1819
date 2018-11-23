
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.FixUpTask;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Test -------------------------------------------------------------------

	@Test
	public void testUpdatePhasePositive() {
		Phase phase;

		phase = this.phaseService.findOne(super.getEntityId("phase8"));

		super.authenticate("handyWorker3");

		phase.setTitle("Título modificado");
		phase.setDescription("Esta fase ha sido modificada para un test");
		this.phaseService.save(phase);

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNotOwnedPhase() {
		Phase phase;

		phase = this.phaseService.findOne(super.getEntityId("phase8"));

		super.authenticate("handyWorker4");

		phase.setTitle("Título modificado");
		phase.setDescription("Esta fase ha sido modificada para un test");
		this.phaseService.save(phase);

		super.unauthenticate();
	}

	@Test
	public void testDeletePhase() {
		Phase phase;
		FixUpTask referedFUT;

		phase = this.phaseService.findOne(super.getEntityId("phase8"));

		super.authenticate("handyWorker3");

		phase.setTitle("Título modificado");
		phase.setDescription("Esta fase ha sido modificada para un test");
		this.phaseService.delete(phase);

		// referedFUT is the FixUpTask which contains phase8
		referedFUT = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask6"));
		Assert.isTrue(!referedFUT.getPhases().contains(phase));

		super.unauthenticate();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNotOwnedPhase() {
		Phase phase;
		FixUpTask referedFUT;

		phase = this.phaseService.findOne(super.getEntityId("phase8"));

		super.authenticate("handyWorker4");

		phase.setTitle("Título modificado");
		phase.setDescription("Esta fase ha sido modificada para un test");
		this.phaseService.delete(phase);

		// referedFUT is the FixUpTask which contains phase8
		referedFUT = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask6"));
		Assert.isTrue(!referedFUT.getPhases().contains(phase));

		super.unauthenticate();
	}
}
