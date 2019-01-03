
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository		noteRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ReportService		reportService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private UtilityService		utilityService;


	// Constructor ------------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Note create() {
		Note result;
		// TODO: A la espera de preguntar al profesor
		//		Authority authReferee, authCustomer, authHandyWorker;
		//		UserAccount principal;
		//
		//		authReferee = new Authority();
		//		authReferee.setAuthority(Authority.REFEREE);
		//		authCustomer = new Authority();
		//		authCustomer.setAuthority(Authority.CUSTOMER);
		//		authHandyWorker = new Authority();
		//		authHandyWorker.setAuthority(Authority.HANDYWORKER);
		//
		//		principal = LoginService.getPrincipal();

		result = new Note();
		result.setMoment(this.utilityService.current_moment());

		//		if (principal.getAuthorities().contains(authHandyWorker))
		//			result.setCommentHandyWorker("");
		//		else if (principal.getAuthorities().contains(authCustomer))
		//			result.setCommentCustomer("");
		//		else if (principal.getAuthorities().contains(authReferee))
		//			result.setCommentReferee("");

		return result;
	}

	public Note save(final Note note) { // Updating
		return this.save(null, note);
	}

	public Note save(Report report, final Note note) { // Creating
		Assert.notNull(note);

		Note result;
		boolean isUpdating;

		isUpdating = this.noteRepository.exists(note.getId());

		if (isUpdating)
			report = this.reportService.findByNoteId(note.getId());

		Assert.notNull(report);
		Assert.isTrue(report.getFinalMode());
		this.utilityService.checkDate(report.getMoment(), note.getMoment());
		this.checkUsersAndComments(report, note);

		result = this.noteRepository.save(note);

		if (!isUpdating)
			this.reportService.addNote(report, result);

		return result;
	}

	public Note findOne(final int noteId) {
		Note result;

		result = this.noteRepository.findOne(noteId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Page<Note> findByReportId(final int reportId, final Pageable pageable) {
		Page<Note> result;

		result = this.noteRepository.findByReportId(reportId, pageable);
		Assert.notNull(result);

		return result;
	}

	public Double[] findDataNumberNotesPerReport() {
		Double[] result;

		result = this.noteRepository.findDataNumberNotesPerReport();

		return result;
	}

	private void checkUsersAndComments(final Report report, final Note note) {
		Authority authReferee, authCustomer, authHandyWorker;
		UserAccount principal;
		Referee referee, refereeInvolved;
		Customer customer, customerInvolved;
		HandyWorker handyWorker, handyWorkerInvolved;

		authReferee = new Authority();
		authReferee.setAuthority(Authority.REFEREE);
		authCustomer = new Authority();
		authCustomer.setAuthority(Authority.CUSTOMER);
		authHandyWorker = new Authority();
		authHandyWorker.setAuthority(Authority.HANDYWORKER);

		principal = LoginService.getPrincipal();

		if (principal.getAuthorities().contains(authReferee)) {
			// Check if the actor is writing the corresponding comment
			Assert.isTrue(note.getCommentReferee() != null);

			// Check if the actor has permission to write on this note
			refereeInvolved = this.refereeService.findByReportId(report.getId());
			referee = this.refereeService.findByUserAccount(principal.getId());
			Assert.isTrue(refereeInvolved.equals(referee));
			this.checkBannedActorMarkAsSuspicious(note, referee);
		} else if (principal.getAuthorities().contains(authCustomer)) {
			// Check if the actor is writing the corresponding comment
			Assert.isTrue(note.getCommentCustomer() != null);

			// Check if the actor has permission to write on this note
			customerInvolved = this.customerService.findCustomerByReport(report.getId());
			customer = this.customerService.findByUserAccount(principal.getId());
			Assert.isTrue(customerInvolved.equals(customer));
			this.checkBannedActorMarkAsSuspicious(note, customer);
		} else if (principal.getAuthorities().contains(authHandyWorker)) {
			// Check if the actor is writing the corresponding comment
			Assert.isTrue(note.getCommentHandyWorker() != null);

			// Check if the actor has permission to write on this note
			handyWorkerInvolved = this.handyWorkerService.findByReportId(report.getId());
			handyWorker = this.handyWorkerService.findByUserAccount(principal.getId());
			Assert.isTrue(handyWorkerInvolved.equals(handyWorker));
			this.checkBannedActorMarkAsSuspicious(note, handyWorker);
		}
	}

	private void checkBannedActorMarkAsSuspicious(final Note note, final Actor principal) {
		this.utilityService.checkIsSpamMarkAsSuspicious(note.getCommentCustomer() + note.getCommentHandyWorker() + note.getCommentReferee(), principal);
		this.utilityService.checkActorIsBanned(principal);
	}
}
