
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private NoteService	noteService;


	// Test -------------------------------------------------------------------

	@Test
	public void testSaveDeleteWarranty() {

	}

	// Test1: [referee] crear, guardar (y comprobar que está insertado en 
	// el report correspondiente) y borrar (y comprobar que no está insertado 
	// en el report correspondiente) normalmente.
	// Test2: [customer] igual que test1
	// Test3: [handyworker] igual que test1
	// Test4: [referee incorrecto]
	// Test5: [customer incorrecto]
	// Test6: [handyworker incorrecto]
}
