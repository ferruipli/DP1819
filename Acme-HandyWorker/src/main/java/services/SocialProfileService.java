
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SocialProfileRepository	socialProfileRepository;


	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public SocialProfileService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SocialProfile create() {
		SocialProfile socialPro;
		socialPro = new SocialProfile();

		return socialPro;
	}

	public SocialProfile findOne(final int IdSocialProfile) {
		Assert.isTrue(IdSocialProfile != 0);
		SocialProfile result;
		result = this.socialProfileRepository.findOne(IdSocialProfile);
		Assert.notNull(result);
		return result;
	}
	public Collection<SocialProfile> findAll() {

		Collection<SocialProfile> result;
		result = this.socialProfileRepository.findAll();
		return result;

	}
	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		SocialProfile result;
		result = this.socialProfileRepository.save(socialProfile);
		Assert.notNull(result);
		Assert.isTrue(result.getId() != 0);
		return result;

	}
	public void delete(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		Assert.notNull(this.socialProfileRepository.findOne(socialProfile.getId()));
		this.socialProfileRepository.delete(socialProfile);

	}

	// Other business methods -------------------------------------------------

}
