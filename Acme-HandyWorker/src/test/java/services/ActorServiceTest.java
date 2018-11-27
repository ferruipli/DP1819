
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	// Tests ----------------------------------------------

	@Test
	public void testFindAll() {
		Collection<Actor> actors;
		actors = this.actorService.findAll();
		Assert.notEmpty(actors);
		Assert.notNull(actors);
		//System.out.println(actors);
	}

	@Test
	public void testFindOne() {
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("customer2"));
		Assert.notNull(actor);
	}

	@Test
	public void testIsBanner() {
		super.authenticate("system");
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("customer2"));
		Assert.isTrue(!(actor.getUserAccount().getIsBanned()));
		this.actorService.isBanner(actor);
		Assert.isTrue(actor.getUserAccount().getIsBanned());
		super.unauthenticate();
	}

	@Test
	public void testNotBanner() {
		super.authenticate("system");
		Actor actor;
		actor = this.actorService.findOne(super.getEntityId("customer2"));
		Assert.isTrue(!(actor.getUserAccount().getIsBanned()));
		this.actorService.isBanner(actor);
		Assert.isTrue(actor.getUserAccount().getIsBanned());
		this.actorService.notBanner(actor);
		Assert.isTrue(!(actor.getUserAccount().getIsBanned()));
		super.unauthenticate();
	}

	@Test
	public void isSuspicious() {
		super.authenticate("customer1");
		Actor sender;
		Actor recipient;
		sender = this.actorService.findPrincipal();
		recipient = this.actorService.findOne(super.getEntityId("customer2"));
		Assert.isTrue(!(sender.getIsSuspicious()));
		Message message;
		message = this.messageService.create();
		message.setBody("Hola éste es el cuerpo del mensaje, viagra");
		message.getRecipients().add(recipient);
		message.setSender(sender);
		message.setSubject("buenas tardes");
		message.setPriority("NEUTRAL");

		message = this.messageService.save(message);
		Assert.isTrue(sender.getIsSuspicious());
		super.unauthenticate();
	}

}
