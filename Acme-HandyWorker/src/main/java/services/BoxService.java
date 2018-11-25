
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxRepository	boxRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public BoxService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Box create() {
		Box result;
		List<Message> messages;
		final Actor actor = this.actorService.findPrincipal();
		Assert.notNull(actor);

		result = new Box();
		messages = new ArrayList<Message>();

		result.setMessages(messages);
		result.setActor(actor);
		result.setIsSystemBox(false);

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> result;

		result = this.boxRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Box findOne(final int boxId) {
		Assert.isTrue(boxId != 0);

		Box result;

		result = this.boxRepository.findOne(boxId);
		Assert.notNull(result);

		return result;
	}

	public Box save(final Box box) {
		Assert.isTrue(!(box.getIsSystemBox()));
		Assert.notNull(box);

		Box result;
		final Actor actor = this.actorService.findPrincipal();

		Assert.notNull(actor);

		if (box.getId() != 0)
			Assert.isTrue(box.getActor().equals(actor));

		Assert.isTrue(!(box.getName().equals("in box")));
		Assert.isTrue(!(box.getName().equals("out box")));
		Assert.isTrue(!(box.getName().equals("trash box")));
		Assert.isTrue(!(box.getName().equals("spam box")));

		result = this.boxRepository.save(box);

		return result;
	}

	//TODO que solo pueda eliminar las suyas
	public void delete(final Box box) {
		Assert.notNull(box);

		final Actor actor = this.actorService.findPrincipal();

		Assert.isTrue(box.getActor().equals(actor));
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(!(box.getIsSystemBox()));

		this.boxRepository.delete(box);
	}

	// Other business methods -------------------------------------------------

	public Collection<Box> createDefaultBox(final Actor actor) {
		Box inbox;
		Box outbox;
		Box trashbox;
		Box spambox;

		Collection<Box> res;
		Collection<Message> messages;

		res = new ArrayList<Box>();
		messages = new ArrayList<>();

		inbox = new Box();
		outbox = new Box();
		trashbox = new Box();
		spambox = new Box();

		inbox.setName("In box");
		outbox.setName("Out box");
		trashbox.setName("Trash box");
		spambox.setName("Spam box");

		inbox.setIsSystemBox(true);
		outbox.setIsSystemBox(true);
		trashbox.setIsSystemBox(true);
		spambox.setIsSystemBox(true);

		inbox.setMessages(messages);
		outbox.setMessages(messages);
		trashbox.setMessages(messages);
		spambox.setMessages(messages);

		inbox.setActor(actor);
		outbox.setActor(actor);
		trashbox.setActor(actor);
		spambox.setActor(actor);

		inbox = this.boxRepository.save(inbox);
		outbox = this.boxRepository.save(outbox);
		trashbox = this.boxRepository.save(trashbox);
		spambox = this.boxRepository.save(spambox);

		res.add(inbox);
		res.add(outbox);
		res.add(trashbox);
		res.add(spambox);

		return res;
	}

	public Box searchBox(final Actor actor, final String nameBox) {
		Box searchBox;
		Assert.isTrue(nameBox.equals("in box") || nameBox.equals("out box") || nameBox.equals("trash box") || nameBox.equals("spam box"));
		searchBox = this.boxRepository.searchBox(actor.getId(), nameBox);
		Assert.notNull(searchBox);
		return searchBox;

	}

	public Collection<Box> findAllBoxByActor(final Actor actor) {
		Collection<Box> res = null;
		res = this.boxRepository.findAllBoxByActor(actor.getId());
		return res;

	}

	public Collection<Box> boxWithMessage(final Message message) {
		Collection<Box> res = null;
		res = this.boxRepository.boxWithMessage(message.getId());
		return res;

	}

}
