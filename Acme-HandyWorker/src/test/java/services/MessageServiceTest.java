
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
import domain.Actor;
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

	@Autowired
	private ActorService	actorService;


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
		Message message;

		final Actor recipient = this.actorService.findOne(super.getEntityId("customer2"));
		final Actor sender = this.actorService.findPrincipal();

		message = this.messageService.create();
		message.setBody("Hola éste es el cuerpo del mensaje, viagra");
		message.getRecipients().add(recipient);
		message.setSender(sender);
		message.setSubject("buenas tardes");
		message.setPriority("NEUTRAL");

		message = this.messageService.save(message);

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
