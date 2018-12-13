
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
import domain.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	//Service under test ----------------------------------
	@Autowired
	private SectionService	sectionService;


	// Test ------------------------------------------------

	@Test
	public void testCreate() {
		final Section section;
		section = this.sectionService.create();
		Assert.notNull(section);
	}
	@Test
	public void testSave() {
		super.authenticate("handyworker1");
		final Section section;
		final Section sectionSaved;

		section = this.sectionService.findOne(super.getEntityId("section1"));

		section.setTitle("title section");

		sectionSaved = this.sectionService.save(section);

		Assert.notNull(sectionSaved);
		super.unauthenticate();
	}

	//Modificar un finder que no el no es el propietario
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSave() {
		super.authenticate("handyworker3");
		final Section section;
		final Section sectionSaved;

		section = this.sectionService.findOne(super.getEntityId("section1"));

		section.setTitle("title section");

		sectionSaved = this.sectionService.save(section);

		Assert.notNull(sectionSaved);
		super.unauthenticate();
	}
	@Test
	public void testDelete() {
		super.authenticate("handyworker1");

		final Section section;
		section = this.sectionService.findOne(super.getEntityId("section1"));
		this.sectionService.delete(section);
		super.unauthenticate();

	}

	@Test
	public void testFindAll() {
		Collection<Section> sections;
		sections = this.sectionService.findAll();
		Assert.notEmpty(sections);
		Assert.notNull(sections);

	}

	@Test
	public void testFindOne() {
		Section section;

		section = this.sectionService.findOne(super.getEntityId("section1"));
		Assert.notNull(section);

	}

}
