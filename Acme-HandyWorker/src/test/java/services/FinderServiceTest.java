
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private FinderService	finderService;


	// Test ------------------------------------------------

	@Test
	public void testCreate() {
		final Finder finder;
		finder = this.finderService.create();
		Assert.notNull(finder);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSave() {
		super.authenticate("handyWorker1");
		final Finder finder;
		final Finder finderSaved;
		Date dateFinder;
		Date dateFinderSaved;

		finder = this.finderService.findOne(super.getEntityId("finder1"));
		dateFinder = finder.getLastUpdate();

		finder.setKeyword("fixUpTask");
		finderSaved = this.finderService.save(finder);
		dateFinderSaved = finderSaved.getLastUpdate();

		Assert.isTrue(dateFinder != dateFinderSaved);
		Assert.notNull(finderSaved);
		super.unauthenticate();
	}

	//Modificar un finder que no el no es el propietario
	@Test(expected = IllegalArgumentException.class)
	public void testNegateSave() {
		super.authenticate("handyWorker5");
		final Finder finder;
		final Finder finderSaved;
		Date dateFinder;
		Date dateFinderSaved;

		finder = this.finderService.findOne(super.getEntityId("finder1"));
		dateFinder = finder.getLastUpdate();

		finder.setKeyword("fixUpTask");
		finderSaved = this.finderService.save(finder);
		dateFinderSaved = finderSaved.getLastUpdate();

		Assert.isTrue(dateFinder != dateFinderSaved);
		Assert.notNull(finderSaved);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Collection<Finder> finders;
		finders = this.finderService.findAll();
		Assert.notEmpty(finders);
		Assert.notNull(finders);

	}

	@Test
	public void testFindOne() {
		Finder finder;

		finder = this.finderService.findOne(super.getEntityId("finder1"));
		Assert.notNull(finder);
	}

	//	@Test(expected = IllegalArgumentException.class)
	//	public void testNegativeSearch() {
	//		super.authenticate("handyworker1");
	//		Finder finder;
	//		Collection<FixUpTask> fixUpTasks;
	//		finder = this.finderService.findOne(super.getEntityId("finder6"));
	//		fixUpTasks = this.finderService.search(finder);
	//		Assert.notNull(fixUpTasks);
	//		super.unauthenticate();
	//
	//	}
	//
	//	@Test
	//	public void testSearch() {
	//		super.authenticate("handyworker6");
	//		Finder finder;
	//		Collection<FixUpTask> fixUpTasks;
	//		finder = this.finderService.findOne(super.getEntityId("finder6"));
	//		fixUpTasks = this.finderService.search(finder);
	//		Assert.notNull(fixUpTasks);
	//		super.unauthenticate();
	//
	//	}

}
