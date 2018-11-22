
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private MessageService	messageService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("HandyWorker1");
		final Message message;
		message = this.messageService.create();
		Assert.notNull(message);
		super.unauthenticate();
	}

}
