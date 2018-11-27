
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

	// Supporting services ----------------------------------------------------

	@Autowired
	private ReportService	reportService;


	// Test -------------------------------------------------------------------

	@Test
	public void testWriteNote1() {
		Note note, saved;
		int reportId;
		Report report;

		reportId = super.getEntityId("report3");
		report = this.reportService.findOne(reportId);

		super.authenticate("referee2");
		note = this.noteService.create();
		note.setCommentReferee("Esto es el comentario de test el creador de la nota");

		saved = this.noteService.save(report, note);
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.getNotes().contains(saved));
		super.unauthenticate();

		super.authenticate("customer5");
		saved.setCommentCustomer("Customer añadiendo un comentario en los tests");
		this.noteService.save(saved);
		super.unauthenticate();

		super.authenticate("handyworker5");
		saved.setCommentHandyWorker("HandyWorker añadiendo un comentario en los tests");
		this.noteService.save(saved);
		super.unauthenticate();
	}

	@Test
	public void testWriteNote2() {
		Note note, saved;
		int reportId;
		Report report;

		reportId = super.getEntityId("report3");
		report = this.reportService.findOne(reportId);

		super.authenticate("customer5");
		note = this.noteService.create();
		note.setCommentCustomer("Esto es el comentario de test el creador de la nota");

		saved = this.noteService.save(report, note);
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.getNotes().contains(saved));
		super.unauthenticate();

		super.authenticate("referee2");
		saved.setCommentReferee("Referee añadiendo un comentario en los tests");
		this.noteService.save(saved);
		super.unauthenticate();

		super.authenticate("handyworker5");
		saved.setCommentHandyWorker("HandyWorker añadiendo un comentario en los tests");
		this.noteService.save(saved);
		super.unauthenticate();
	}

	// Incorrect Customer
	@Test(expected = IllegalArgumentException.class)
	public void testWriteNoteNegative() {
		Note note, saved;
		int reportId;
		Report report;

		reportId = super.getEntityId("report3");
		report = this.reportService.findOne(reportId);

		super.authenticate("handyworker5");
		note = this.noteService.create();
		note.setCommentHandyWorker("Esto es el comentario de test el creador de la nota");

		saved = this.noteService.save(report, note);
		report = this.reportService.findOne(reportId);
		Assert.isTrue(report.getNotes().contains(saved));
		super.unauthenticate();

		super.authenticate("referee2");
		saved.setCommentReferee("Referee añadiendo un comentario en los tests");
		this.noteService.save(saved);
		super.unauthenticate();

		super.authenticate("customer1");
		saved.setCommentCustomer("Customer añadiendo un comentario en los tests");
		this.noteService.save(saved);
		super.unauthenticate();
	}

	// Incorrect Handy Worker
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect1() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note5"));

		super.authenticate("handyworker1");

		note.setCommentHandyWorker("Handy Worker añadiendo un comentario en los tests");
		this.noteService.save(note);

		super.unauthenticate();
	}

	// Incorrect Referee
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect2() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note5"));

		super.authenticate("referee3");

		note.setCommentReferee("Referee añadiendo un comentario en los tests");
		this.noteService.save(note);

		super.unauthenticate();
	}

	// Customer comment in the incorrect field
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect3() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note5"));

		super.authenticate("customer5");

		note.setCommentReferee("Customer añadiendo un comentario en los tests");
		this.noteService.save(note);

		super.unauthenticate();
	}

	// Handy Worker comment in the incorrect field
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect4() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note5"));

		super.authenticate("handyworker5");

		note.setCommentCustomer("Handy Worker añadiendo un comentario en los tests");
		this.noteService.save(note);

		super.unauthenticate();
	}

	// Referee comment in the incorrect field
	@Test(expected = IllegalArgumentException.class)
	public void testCommentIncorrect5() {
		Note note;

		note = this.noteService.findOne(super.getEntityId("note5"));

		super.authenticate("referee2");

		note.setCommentHandyWorker("Referee añadiendo un comentario en los tests");
		this.noteService.save(note);

		super.unauthenticate();
	}
}
