
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed repository ---------------------------------------------
	@Autowired
	private FinderRepository	finderRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private FinderService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Finder create() {
		Finder result;

		result = new Finder();

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		Finder result;

		result = this.finderRepository.save(finder);

		return result;
	}

	public Finder findOne(final int idFinder) {
		Finder result;

		Assert.isTrue(idFinder != 0);

		result = this.finderRepository.findOne(idFinder);

		return result;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Finder finder) {
		Assert.isTrue(finder.getId() != 0);
		Assert.notNull(finder);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	//Other business methods-------------------------------------------
}
