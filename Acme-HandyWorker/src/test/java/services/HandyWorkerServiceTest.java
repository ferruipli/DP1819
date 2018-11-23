
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
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Test ------------------------------------------------
	@Test
	public void testCreate() {
		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.create();
		Assert.notNull(handyWorker);
	}
	@Test
	public void testSave() {
		super.authenticate("handyworker2");
		final HandyWorker handyWorker;
		final HandyWorker handyWorkerSaved;

		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyworker2"));

		handyWorker.setAddress("Francisco de Quevedo 12");
		handyWorker.setEmail("maria@gmail.com");
		handyWorker.setSurname("Jim�nez");

		handyWorkerSaved = this.handyWorkerService.save(handyWorker);

		Assert.notNull(handyWorkerSaved);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Collection<HandyWorker> handyWorkers;
		handyWorkers = this.handyWorkerService.findAll();
		Assert.notEmpty(handyWorkers);
		Assert.notNull(handyWorkers);

	}

	@Test
	public void testFindOne() {
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyworker2"));
		Assert.notNull(handyWorker);

	}

}
