
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private WarrantyRepository	warrantyRepository;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public WarrantyService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Warranty create() {
		Warranty result;

		result = new Warranty();

		return result;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> result;

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Warranty save(final Warranty warranty) {
		Assert.notNull(warranty);
		Assert.isTrue(!warranty.getFinalMode());

		Warranty result;

		result = this.warrantyRepository.save(warranty);

		return result;
	}

	public void delete(final Warranty warranty) {
		Assert.notNull(warranty);
		Assert.isTrue(this.warrantyRepository.exists(warranty.getId()));
		Assert.isTrue(!warranty.getFinalMode());

		this.warrantyRepository.delete(warranty);
	}

	public Warranty findOne(final int warrantyId) {
		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------
}
