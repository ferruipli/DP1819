
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository ---------------------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;


	// Supporting services -------------------------------------------

	//Constructor ----------------------------------------------------
	private SponsorService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Sponsor create() {
		Sponsor result;

		result = new Sponsor();

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		Sponsor result;

		result = this.sponsorRepository.save(sponsor);

		return result;
	}

	public Sponsor findOne(final int idSponsor) {
		Sponsor result;

		Assert.isTrue(idSponsor != 0);

		result = this.sponsorRepository.findOne(idSponsor);

		return result;
	}
	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();

		Assert.notNull(result);

		return result;
	}
	public void delete(final Sponsor sponsor) {
		Assert.isTrue(sponsor.getId() != 0);
		Assert.notNull(sponsor);
		Assert.isTrue(this.sponsorRepository.exists(sponsor.getId()));

		this.sponsorRepository.delete(sponsor);
	}
	//Other business methods-------------------------------------------

}
