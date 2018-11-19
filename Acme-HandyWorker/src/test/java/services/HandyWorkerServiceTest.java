
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest {

	//Service under test ----------------------------------
	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Test ------------------------------------------------
	@Test
	@Rollback
	public void createTestValid() {
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.create();

		handyWorker.setAddress("Calle FranDEqUE");
		handyWorker = this.handyWorkerService.save(handyWorker);
	}

}
