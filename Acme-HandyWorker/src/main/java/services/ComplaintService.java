
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.FixUpTask;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UtilityService		utilityService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructor ------------------------------------------------------------

	public ComplaintService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Complaint create() {
		Complaint result;
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Complaint();
		result.setTicker(this.utilityService.generateValidTicker());
		result.setMoment(moment);

		return result;
	}

	public Complaint save(final Complaint complaint) {
		Complaint result;
		FixUpTask fixUpTask;

		fixUpTask = complaint.getFixUpTask();
		Assert.notNull(fixUpTask);

		result = this.complaintRepository.save(complaint);
		this.fixUpTaskService.addComplaint(fixUpTask, result);

		return result;
	}

	// COMPLT: método de búsqueda de los Complaints que no están asignados.

	// Other business methods -------------------------------------------------

}
