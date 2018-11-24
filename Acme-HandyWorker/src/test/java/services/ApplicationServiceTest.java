
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
import domain.Application;
import domain.FixUpTask;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Test ------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("handyWorker1");
		final Application application;
		FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask4"));
		application = this.applicationService.create(fixUpTask);
		Assert.isTrue(application.getStatus().equals("PENDING"));
		Assert.notNull(application);
		super.unauthenticate();
	}
	@Test
	public void testSave() {
		super.authenticate("handyworker2");
		final Application application;
		final Application applicationSaved;

		application = this.applicationService.findOne(super.getEntityId("application1"));
		application.setStatus("ACCEPTED");

		applicationSaved = this.applicationService.save(application);

		Assert.notNull(applicationSaved);
		Assert.isTrue(applicationSaved.getStatus().equals("ACCEPTED"));
		super.unauthenticate();
	}
	@Test
	public void testFindAll() {
		Collection<Application> applications;
		applications = this.applicationService.findAll();
		Assert.notEmpty(applications);
		Assert.notNull(applications);

	}

	@Test
	public void testFindOne() {
		Application application;

		application = this.applicationService.findOne(super.getEntityId("application2"));
		Assert.notNull(application);

	}
	@Test
	public void testDelete() {
		super.authenticate("handyworker1");

		final Application application;
		FixUpTask fixUpTask;
		HandyWorker handyWorker;
		application = this.applicationService.findOne(super.getEntityId("application2"));
		fixUpTask = application.getFixUpTask();
		handyWorker = application.getHandyWorker();
		this.applicationService.delete(application);
		Assert.isTrue(!(fixUpTask.getApplications().contains(application)));
		Assert.isTrue(!(handyWorker.getApplications().contains(application)));
		super.unauthenticate();

	}

}
