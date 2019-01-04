
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed repository ---------------------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting services -------------------------------------------

	@Autowired
	private ActorService		actorService;


	//Constructor ----------------------------------------------------
	public SponsorService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Sponsor create() {
		Sponsor result;
		Collection<Sponsorship> sponsorships;

		sponsorships = new ArrayList<Sponsorship>();

		result = new Sponsor();
		result.setUserAccount(this.actorService.createUserAccount(Authority.REFEREE));
		result.setSponsorships(sponsorships);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Sponsor result;

		result = (Sponsor) this.actorService.save(sponsor);

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

	//Other business methods-------------------------------------------

	public Sponsor findByPrincipal() {
		Sponsor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	public Sponsor findByUserAccount(final int userAccountId) {
		Sponsor result;

		result = this.sponsorRepository.findByUserAccount(userAccountId);

		return result;
	}
	protected Sponsor findSponsorBySponsorshipId(final int id) {
		Sponsor sponsor;

		sponsor = this.sponsorRepository.findSponsorBySponsorshipId(id);

		return sponsor;
	}

}
