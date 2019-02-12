
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
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private TutorialService	tutorialService;


	// Test ------------------------------------------------
	@Test
	public void testCreate() {
		super.authenticate("handyworker5");
		final Tutorial tutorial;

		tutorial = this.tutorialService.create();
		Assert.notNull(tutorial);
		super.unauthenticate();
	}
	@Test
	public void testSave() {
		super.authenticate("handyworker1");
		final Tutorial tutorial;
		final Tutorial tutorialSaved;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));

		tutorial.setTitle("title tutorial");

		tutorialSaved = this.tutorialService.save(tutorial);

		Assert.notNull(tutorialSaved);
		super.unauthenticate();
	}

	//Modificar un finder que no el no es el propietario
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSave() {
		super.authenticate("handyworker3");
		final Tutorial tutorial;
		final Tutorial tutorialSaved;

		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));

		tutorial.setTitle("title tutorial");

		tutorialSaved = this.tutorialService.save(tutorial);

		Assert.notNull(tutorialSaved);
		super.unauthenticate();
	}
	@Test
	public void testDelete() {
		super.authenticate("handyworker1");
		final Tutorial tutorial;
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.tutorialService.delete(tutorial);
		super.unauthenticate();

	}
	//Modificar un finder que no el no es el propietario
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeDelete() {
		super.authenticate("handyworker4");
		final Tutorial tutorial;
		tutorial = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.tutorialService.delete(tutorial);
		super.unauthenticate();

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

}
