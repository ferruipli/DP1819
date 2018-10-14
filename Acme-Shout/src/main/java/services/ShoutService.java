/*
 * AnnouncementService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShoutRepository;
import domain.Actor;
import domain.Shout;

@Service
@Transactional
public class ShoutService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShoutRepository	shoutRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public ShoutService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Shout create() {
		Shout result;
		Actor principal;

		principal = this.actorService.findByPrincipal();

		result = new Shout();
		result.setCreator(principal);
		result.setUsername(principal.getUserAccount().getUsername());

		return result;
	}

	public Collection<Shout> findAll() {
		Collection<Shout> result;

		Assert.notNull(this.shoutRepository);
		result = this.shoutRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Shout save(final Shout shout) {
		Assert.notNull(shout);

		Shout result;

		result = this.shoutRepository.save(shout);

		return result;
	}

	// Other business methods -------------------------------------------------
	
	public Map<String, Double> computeStatistics() {
		Map<String, Double> result;
		double countAll, countShort, countLong;

		countAll = this.shoutRepository.countAllShouts();
		countShort = this.shoutRepository.countShortShouts();
		countLong = this.shoutRepository.countLongShouts();

		result = new HashMap<String, Double>();
		result.put("count.all.shouts", countAll);
		result.put("count.short.shouts", countShort);
		result.put("count.long.shouts", countLong);

		return result;
	}

}
