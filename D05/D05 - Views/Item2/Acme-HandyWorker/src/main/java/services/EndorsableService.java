
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsableRepository;
import security.LoginService;
import security.UserAccount;
import domain.Endorsable;
import domain.Endorsement;

@Service
@Transactional
public class EndorsableService {

	// Managed repository ------------------------------
	@Autowired
	private EndorsableRepository	endorsableRepository;

	// Supporting services -----------------------------
	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private CustomisationService	customisationService;


	// Constructors ------------------------------------
	public EndorsableService() {
		super();
	}

	// Simple CRUD methods -----------------------------
	public Endorsable findOne(final int endorsableId) {
		Endorsable result;

		result = this.endorsableRepository.findOne(endorsableId);

		return result;
	}

	// Other business methods --------------------------
	// Los endorsements de un endorsable son los que recibe.
	public void computeScore(final Endorsable endorsable) {
		Assert.notNull(endorsable);
		Assert.isTrue(endorsable.getId() != 0);

		final Double score;
		Integer p, n;
		final Collection<Endorsement> receivedEndorsements = this.endorsementService.findReceivedEndorsements();
		List<Integer> ls;

		ls = new ArrayList<>(this.positiveNegativeWordNumbers(receivedEndorsements));
		p = ls.get(0);
		n = ls.get(1);

		score = (p - n) / this.max(p, n);

		Assert.isTrue(score >= -1.00 && score <= 1.00);

		endorsable.setScore(score);
	}

	public Endorsable findByPrincipal() {
		UserAccount userAccount;
		Endorsable result;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	// Private methods 
	private Endorsable findByUserAccount(final int userAccountId) {
		Endorsable result;

		result = this.endorsableRepository.findByUserAccount(userAccountId);

		return result;
	}

	private Double max(final Integer n, final Integer p) {
		Double result;

		if (n >= p)
			result = n * 1.0;
		else
			result = p * 1.0;

		return result;
	}

	private List<Integer> positiveNegativeWordNumbers(final Collection<Endorsement> receivedEndorsements) {
		Assert.isTrue(receivedEndorsements != null && receivedEndorsements.size() > 0);

		final List<Integer> results = new ArrayList<Integer>();
		String comments = "";
		String[] words = {};
		Integer positive = 0, negative = 0;
		final List<String> positive_ls = new ArrayList<>(this.customisationService.find().getPositiveWords());
		final List<String> negative_ls = new ArrayList<>(this.customisationService.find().getNegativeWords());

		for (final Endorsement e : receivedEndorsements) {
			comments = e.getComments();
			words = comments.split(" ");

			for (final String word : words)
				if (positive_ls.contains(word.toLowerCase()))
					positive++;
				else if (negative_ls.contains(word.toLowerCase()))
					negative++;

		}

		results.add(positive);
		results.add(negative);

		return results;
	}

}
