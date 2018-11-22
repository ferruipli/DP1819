
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Box;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private BoxService	boxService;


	//Tests ----------------------------------------------
	@Test
	public void testCreate() {
		final Box box;
		box = this.boxService.create();
		Assert.notNull(box);
	}

	@Test
	public void testSave() {
		final Box box;
		final Box boxSaved;

		box = this.boxService.findOne(super.getEntityId("box23"));

		box.setName("amigos box");

		boxSaved = this.boxService.save(box);

		Assert.notNull(boxSaved);
	}

	@Test
	public void testDelete() {
		final Box box;
		box = this.boxService.findOne(super.getEntityId("box23"));
		this.boxService.delete(box);

	}

}
