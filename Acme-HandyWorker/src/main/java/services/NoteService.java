
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
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);

		result = new Note();
		result.setMoment(moment);

		return result;
	}

	public Note save(final int reportId, final Note note) {
		Note result;
		Report report;

		Assert.isTrue(!this.noteRepository.exists(note.getId()));
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.getFinalMode());
		this.checkUsersAndComments(report, note);

		result = this.noteRepository.save(note);
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

	public void writeComment(final Note note) {
		Report report;

		Assert.isTrue(this.noteRepository.exists(note.getId()));
		report = this.reportService.findByNoteId(note.getId());
		this.checkUsersAndComments(report, note);

		this.noteRepository.save(note);
	}

	private void checkUsersAndComments(final Report report, final Note note) {
		Authority authReferee, authCustomer, authHandyWorker;
		UserAccount principal;
		Referee referee;
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
			Assert.isTrue(!note.getIsCommentedByReferee());
			referee = this.refereeService.findByUserAccount(principal.getId()); // COMPLT: quiza sobra porque solo tengo en referee principal solo para obtener su id?? Igual para customer y handyworker
			Assert.isTrue(this.reportService.findByRefereeId(referee.getId()).contains(report));
			Assert.isTrue(!this.isEmpty(note.getCommentReferee()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentHandyWorker()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentCustomer()));

			note.setIsCommentedByReferee(true);
		} else if (principal.getAuthorities().contains(authCustomer)) {
			Assert.isTrue(!note.getIsCommentedByCustomer());
			customerInvolved = this.customerService.findCustomerByComplaint(report.getId()); // COMPLT: cambiar el nombre de la consulta a findCustomerByReport
			customer = this.customerService.findByUserAccount(principal.getId());
			Assert.isTrue(customerInvolved.equals(customer));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentReferee()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentHandyWorker()));
			Assert.isTrue(!this.isEmpty(note.getCommentCustomer()));

			note.setIsCommentedByCustomer(true);
		} else if (principal.getAuthorities().contains(authHandyWorker)) {
			Assert.isTrue(!note.getIsCommentedByHandyWorker());
			handyWorkerInvolved = this.handyWorkerService.findByReportId(report.getId());
			handyWorker = this.handyWorkerService.findByUserAccount(principal.getId());
			Assert.isTrue(handyWorkerInvolved.equals(handyWorker));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentReferee()));
			Assert.isTrue(!this.isEmpty(note.getCommentHandyWorker()));
			Assert.isTrue(this.noteRepository.exists(note.getId()) || this.isEmpty(note.getCommentCustomer()));

			note.setIsCommentedByHandyWorker(true);
		}
	}

	private boolean isEmpty(final String str) {
		return str == null || str.isEmpty();
	}
}
