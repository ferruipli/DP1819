
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository	noteRepository;


	// Supporting services ----------------------------------------------------

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

	public Note save(final Note note) {
		Note result;

		result = this.noteRepository.save(note);

		return result;
	}

	public void delete(final Note note) {
		this.noteRepository.delete(note);
	}

	public Note findOne(final int noteId) {
		Note result;

		result = this.noteRepository.findOne(noteId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods--------------------------------------------------
}
