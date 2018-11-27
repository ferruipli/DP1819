
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
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


	// Constructor ------------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Note create() {
		Note result;

		result = new Note();

		return result;
	}

	public Note save(final Note note) { // Updating
		return this.save(null, note);
	}

	public Note save(Report report, final Note note) { // Creating
		Assert.notNull(note);

		Note result;
		Date moment;
		boolean isUpdating;

		isUpdating = this.noteRepository.exists(note.getId());

		if (isUpdating)
			report = this.reportService.findByNoteId(note.getId());
		else {
			moment = new Date(System.currentTimeMillis() - 1);
			note.setMoment(moment);
		}

		Assert.notNull(report);
		Assert.isTrue(report.getFinalMode());
		Assert.isTrue(note.getMoment().after(report.getMoment()));
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
			// Check if the actor is writing the comment in the correct attribute and/or he is not writing a comment again.
			Assert.isTrue(!note.getIsCommentedByReferee());
			Assert.isTrue(!this.isEmpty(note.getCommentReferee()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentHandyWorker()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentCustomer()));

			// Check if the actor has permission to write on this note
			refereeInvolved = this.refereeService.findByReportId(report.getId());
			referee = this.refereeService.findByUserAccount(principal.getId());
			Assert.isTrue(refereeInvolved.equals(referee));

			note.setIsCommentedByReferee(true);
		} else if (principal.getAuthorities().contains(authCustomer)) {
			// Check if the actor is writing the comment in the correct attribute and/or he is not writing a comment again.
			Assert.isTrue(!note.getIsCommentedByCustomer());
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentReferee()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentHandyWorker()));
			Assert.isTrue(!this.isEmpty(note.getCommentCustomer()));

			// Check if the actor has permission to write on this note
			customerInvolved = this.customerService.findCustomerByReport(report.getId());
			customer = this.customerService.findByUserAccount(principal.getId());
			Assert.isTrue(customerInvolved.equals(customer));

			note.setIsCommentedByCustomer(true);
		} else if (principal.getAuthorities().contains(authHandyWorker)) {
			// Check if the actor is writing the comment in the correct attribute and/or he is not writing a comment again.
			Assert.isTrue(!note.getIsCommentedByHandyWorker());
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentReferee()));
			Assert.isTrue(!this.isEmpty(note.getCommentHandyWorker()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentCustomer()));

			// Check if the actor has permission to write on this note
			handyWorkerInvolved = this.handyWorkerService.findByReportId(report.getId());
			handyWorker = this.handyWorkerService.findByUserAccount(principal.getId());
			Assert.isTrue(handyWorkerInvolved.equals(handyWorker));

			note.setIsCommentedByHandyWorker(true);
		}
	}

	private boolean isEmpty(final String str) {
		return str == null || str.isEmpty();
	}
}
