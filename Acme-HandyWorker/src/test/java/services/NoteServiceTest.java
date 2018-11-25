
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ReportService	reportService;


	// Test -------------------------------------------------------------------

	@Test
	public void testWriteNote1() {
		Note note, saved;
		int reportId;
		Report report;

		reportId = super.getEntityId("report3");

		super.authenticate("referee1");
		note = this.noteService.create();
		note.setCommentReferee("Esto es el comentario de test el creador de la nota");

		saved = this.noteService.save(reportId, note);
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.equals(saved));
		super.unauthenticate();

		super.authenticate("customer6");
		saved.setCommentCustomer("Customer a�adiendo un comentario en los tests");
		this.noteService.writeComment(saved);
		super.unauthenticate();

		super.authenticate("handyworker6");
		saved.setCommentHandyWorker("HandyWorker a�adiendo un comentario en los tests");
		this.noteService.writeComment(saved);
		super.unauthenticate();
	}

	@Test
	public void testWriteNote2() {
		Note note, saved;
		int reportId;
		Report report;

		reportId = super.getEntityId("report1");

		super.authenticate("customer6");
		note = this.noteService.create();
		note.setCommentReferee("Esto es el comentario de test el creador de la nota");

		saved = this.noteService.save(reportId, note);
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.equals(saved));
		super.unauthenticate();

		super.authenticate("referee1");
		saved.setCommentReferee("Referee a�adiendo un comentario en los tests");
		this.noteService.writeComment(saved);
		super.unauthenticate();

		super.authenticate("handyworker6");
		saved.setCommentHandyWorker("HandyWorker a�adiendo un comentario en los tests");
		this.noteService.writeComment(saved);
		super.unauthenticate();
	}

	// Incorrect Customer
	@Test(expected = IllegalArgumentException.class)
	public void testWriteNoteNegative() {
		Note note, saved;
		int reportId;
		Report report;

		reportId = super.getEntityId("report1");

		super.authenticate("handyworker6");
		note = this.noteService.create();
		note.setCommentReferee("Esto es el comentario de test el creador de la nota");

		saved = this.noteService.save(reportId, note);
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.equals(saved));
		super.unauthenticate();

		super.authenticate("referee1");
		saved.setCommentReferee("Referee a�adiendo un comentario en los tests");
		this.noteService.writeComment(saved);
		super.unauthenticate();

		super.authenticate("customer1");
		saved.setCommentCustomer("Customer a�adiendo un comentario en los tests");
		this.noteService.writeComment(saved);
		super.unauthenticate();
	}

	// Incorrect Handy Worker
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect1() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note1"));

		super.authenticate("handyworker1");

		note.setCommentHandyWorker("Handy Worker a�adiendo un comentario en los tests");
		this.noteService.writeComment(note);

		super.unauthenticate();
	}

	// Incorrect Referee
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect2() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note1"));

		super.authenticate("referee4");

		note.setCommentReferee("Referee a�adiendo un comentario en los tests");
		this.noteService.writeComment(note);

		super.unauthenticate();
	}

	// Customer comment in the incorrect field
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect3() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note1"));

		super.authenticate("customer");

		note.setCommentReferee("Customer a�adiendo un comentario en los tests");
		this.noteService.writeComment(note);

		super.unauthenticate();
	}

	// Handy Worker comment in the incorrect field
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect4() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note1"));

		super.authenticate("handyworker");

		note.setCommentCustomer("Handy Worker a�adiendo un comentario en los tests");
		this.noteService.writeComment(note);

		super.unauthenticate();
	}

	// Referee comment in the incorrect field
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect5() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note1"));

		super.authenticate("referee4");

		note.setCommentHandyWorker("Referee a�adiendo un comentario en los tests");
		this.noteService.writeComment(note);

		super.unauthenticate();
	}
}
