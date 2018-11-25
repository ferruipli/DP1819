
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message result;
		List<Actor> recipients;
		final Actor sender = this.actorService.findPrincipal();
		Date sendMoment;
		Assert.notNull(sender);

		result = new Message();
		recipients = new ArrayList<Actor>();
		sendMoment = new Date();

		result.setSender(sender);
		result.setRecipients(recipients);
		result.setSendMoment(sendMoment);

		return result;
	}

	public Message findOne(final int IdMessage) {
		Assert.isTrue(IdMessage != 0);
		Message result;
		result = this.messageRepository.findOne(IdMessage);
		Assert.notNull(result);
		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;
		result = this.messageRepository.findAll();
		return result;
	}

	public Message save(final Message message) {
		Assert.notNull(message.getSender());
		Assert.notNull(message.getRecipients());
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0);

		Date sendMoment;
		sendMoment = new Date();
		message.setSendMoment(sendMoment);

		final Message result;
		final Actor sender = this.actorService.findPrincipal();
		final Collection<Actor> recipients = message.getRecipients();

		Assert.isTrue(message.getSender().equals(sender));

		if (this.isSpamMessage(message))
			for (final Actor r : recipients) {
				message.setIsSpam(true);
				final Box spamBoxRecipiens = this.boxService.searchBox(r, "spam box");
				spamBoxRecipiens.getMessages().add(message);
				System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
			}
		else
			for (final Actor r : recipients) {
				final Box inBoxRecipiens = this.boxService.searchBox(r, "in box");
				inBoxRecipiens.getMessages().add(message);
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			}
		result = this.messageRepository.save(message);

		final Box outBoxSender = this.boxService.searchBox(sender, "out box");
		outBoxSender.getMessages().add(message);

		Assert.notNull(result);
		return result;
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.notNull(this.messageRepository.findOne(message.getId()));
		this.messageRepository.delete(message);

	}

	// Other business methods -------------------------------------------------

	public boolean isSpamMessage(final Message message) {
		boolean res = false;
		final Collection<String> spamWords = this.customisationService.find().getSpamWords();
		for (final String sw : spamWords)
			if (message.getSubject().contains(sw) || message.getBody().contains(sw)) {
				res = true;
				break;
			}
		return res;
	}
}
