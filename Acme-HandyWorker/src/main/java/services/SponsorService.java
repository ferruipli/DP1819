
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
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

	//Constructor ----------------------------------------------------
	public SponsorService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Sponsor create() {
		Sponsor result;
		UserAccount userAccount;
		Collection<Sponsorship> sponsorships;

		sponsorships = new ArrayList<Sponsorship>();
		userAccount = new UserAccount();

		result = new Sponsor();
		result.setUserAccount(userAccount);
		result.setSponsorships(sponsorships);

		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Sponsor result;
		final Md5PasswordEncoder encoder;
		final String passwordHash;
		UserAccount userAccount;

		encoder = new Md5PasswordEncoder();
		passwordHash = encoder.encodePassword(sponsor.getUserAccount().getPassword(), null);
		sponsor.getUserAccount().setPassword(passwordHash);

		if (sponsor.getId() == 0)
			//TODO
			//actorService.initializeSystemBox();
			result = this.sponsorRepository.save(sponsor);
		else {
			userAccount = LoginService.getPrincipal();
			Assert.isTrue(userAccount.equals(sponsor.getUserAccount()));
			result = this.sponsorRepository.save(sponsor);
		}

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
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return result;
	}
}
