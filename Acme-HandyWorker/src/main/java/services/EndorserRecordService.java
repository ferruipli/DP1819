
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	// Managed repository ------------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	// Supporting services -----------------------------

	// Constructors ------------------------------------

	public EndorserRecordService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public EndorserRecord create() {
		EndorserRecord result;

		result = new EndorserRecord();

		return result;
	}

	// Other business methods --------------------------
}
