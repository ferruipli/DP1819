
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
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Test ------------------------------------------------
	@Test
	public void testCreate() {
		super.authenticate("handyworker2");
		final Tutorial tutorial;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyworker2"));
		tutorial = this.tutorialService.create();
		Assert.notNull(tutorial);
		super.unauthenticate();
	}
	@Test
	public void testSave() {
		final Tutorial tutorial;
		final Tutorial tutorialSaved;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));

		tutorial.setTitle("title tutorial");

		tutorialSaved = this.tutorialService.save(tutorial);

		Assert.notNull(tutorialSaved);
	}
	@Test
	public void testDelete() {

		final Tutorial tutorial;
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.tutorialService.delete(tutorial);

	}

	@Test
	public void testFindAll() {
		Collection<Tutorial> tutorials;
		tutorials = this.tutorialService.findAll();
		Assert.notEmpty(tutorials);
		Assert.notNull(tutorials);

	}

	@Test
	public void testFindOne() {
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Assert.notNull(tutorial);

	}

	//TODO: TESTADDSECTION TESTREMOVESECTION
}
