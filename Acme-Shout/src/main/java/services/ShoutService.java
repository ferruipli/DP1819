
package services;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ShoutRepository;

@Service
@Transactional
public class ShoutService {

	@Autowired
	private ShoutRepository	shoutRepository;


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
