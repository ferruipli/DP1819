
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Box;

@Service
@Transactional
public class BoxService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxRepository	boxRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BoxService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Box create() {
		Box box;
		box = new Box();

		return box;
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
		Assert.notNull(box);
		Assert.isTrue(box.getName() != "in box");
		Assert.isTrue(box.getName() != "out box");
		Assert.isTrue(box.getName() != "trash box");
		Assert.isTrue(box.getName() != "spam box");
		Box result;

		result = this.boxRepository.save(box);

		return result;
	}

	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(box.getName() != "in box");
		Assert.isTrue(box.getName() != "out box");
		Assert.isTrue(box.getName() != "trash box");
		Assert.isTrue(box.getName() != "spam box");
		Assert.isTrue(this.boxRepository.exists(box.getId()));

		this.boxRepository.delete(box);
	}

	// Other business methods -------------------------------------------------

}
