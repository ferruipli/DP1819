
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
		super.authenticate("HandyWorker1");
		final Box box;
		box = this.boxService.create();
		Assert.notNull(box);
		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("customer2");
		final Box box;
		final Box boxSaved;

		box = this.boxService.findOne(super.getEntityId("box024"));

		box.setName("amigos box");

		boxSaved = this.boxService.save(box);

		Assert.notNull(boxSaved);
		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("handyworker1");
		final Box box;
		box = this.boxService.findOne(super.getEntityId("box012"));
		Assert.notNull(box);
		this.boxService.delete(box);
		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Collection<Box> boxs;
		boxs = this.boxService.findAll();
		Assert.notEmpty(boxs);
		Assert.notNull(boxs);
	}

	@Test
	public void testFindOne() {
		Box box;
		box = this.boxService.findOne(super.getEntityId("box23"));
		Assert.notNull(box);
	}

}
