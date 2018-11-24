
package services;

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
public class ReportServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private ReportService	reportService;

	// Test -------------------------------------------------------------------

	//	@Test
	//	public void testSaveDeleteReport() {
	//		Report report, saved;
	//		
	//		report = this.reportService.create();
	//		report.setDescription("Esto es una descripción de prueba");
	//		
	//		saved = this.reportService.save(report);
	//	}

	// Test1: crear, guardar (y comprobar que está insertado en el complaint correspondiente)
	// y borrar (y comprobar que no está insertado en el complaint correspondiente) normalmente.
	// Test2: referee actualiza un report en uno de sus complaints
	// Test3: [PETE] referee guarda un report en un complaint que no tiene asignado
}
