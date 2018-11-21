
package services;

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
	@Test
	public void testCompareTime() {
		Finder finder;
		Boolean res;

		finder = this.finderService.findOne(super.getEntityId("finder2"));
		res = this.finderService.compareTime(finder.getLastUpdate(), 1);
		Assert.isTrue(!res);

	}
}
