
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

		result = new Box();
		messages = new ArrayList<Message>();

		result.setMessages(messages);
		result.setActor(actor);

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> result;

		result = this.boxRepository.findAll();

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
		Box result;
		final Actor actor = this.actorService.findPrincipal();

		Assert.isTrue(!(box.getIsSystemBox()));
		Assert.notNull(box);
		//Assert.notNull(actor);
		//si el id != 0 , esa box sea del mismo actor q está modificando
		if (box.getId() == 0)
			Assert.isTrue(this.boxInActor(box, actor));

		result = this.boxRepository.save(box);

		return result;
	}

	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(!(box.getName().equals("in box")));
		Assert.isTrue(!(box.getName().equals("out box")));
		Assert.isTrue(!(box.getName().equals("trash box")));
		Assert.isTrue(!(box.getName().equals("spam box")));
		Assert.isTrue(this.boxRepository.exists(box.getId()));

		this.boxRepository.delete(box);
	}

	// Other business methods -------------------------------------------------

	//si es TRUE = existe una box con ese nombre y ese actor
	public boolean boxInActor(final Box box, final Actor actor) {
		boolean res = true;

		if ((this.boxRepository.existNameboxForActor(box.getName(), actor.getId()).isEmpty()))
			res = false;

		return res;
	}

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

}
