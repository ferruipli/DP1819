
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ReportRepository;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	public ReportService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods--------------------------------------------------
}
