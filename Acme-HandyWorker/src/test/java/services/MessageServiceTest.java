
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

	@Test
	public void testFindOne() {
		Message message;
		message = this.messageService.findOne(super.getEntityId("message1"));
		Assert.notNull(message);
	}

	@Test
	public void testFindAll() {
		Collection<Message> messages;
		messages = this.messageService.findAll();
		Assert.notEmpty(messages);
		Assert.notNull(messages);
	}

	@Test
	public void testSave() {
		super.authenticate("customer1");
		final Message message;
		final Message messageSaved;

		message = new Message();
		message.setBody("Hola este es el cuerpo del mensaje, viagra");
		message.setRecipients("")
		

		messageSaved = this.messageService.save(message);

		Assert.notNull(messageSaved);
		super.unauthenticate();
	}
	@Test
	public void testDelete() {
		final Message message;
		message = this.messageService.findOne(super.getEntityId("message1"));
		Assert.notNull(message);
		this.messageService.delete(message);
	}

}
