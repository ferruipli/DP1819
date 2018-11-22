
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
import domain.FixUpTask;

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

	@Test
	public void testSave() {
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
	}
	@Test
	public void testDelete() {

		final Finder finder;
		finder = this.finderService.findOne(super.getEntityId("finder1"));
		this.finderService.delete(finder);

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

	@Test
	public void testSearch() {
		Finder finder;
		Collection<FixUpTask> fixUpTasks;
		final Date date = new Date();
		finder = this.finderService.findOne(super.getEntityId("finder3"));
		finder.setKeyword("181022-ABC001");
		finder.setStartPrice(22.2);
		fixUpTasks = this.finderService.search(finder);
		Assert.notNull(fixUpTasks);

	}
}
