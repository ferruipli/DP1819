
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message message;
		message = new Message();

		return message;
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
		Assert.notNull(message);
		Message result;
		result = this.messageRepository.save(message);
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);
		return result;

	}
	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.notNull(this.messageRepository.findOne(message.getId()));
		this.messageRepository.delete(message);

	}

	// Other business methods -------------------------------------------------

}
