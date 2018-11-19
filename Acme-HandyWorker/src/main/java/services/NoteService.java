
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.NoteRepository;

@Service
@Transactional
public class NoteService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private NoteRepository	noteRepository;


	// Supporting services ----------------------------------------------------

	// Constructor ------------------------------------------------------------

	private NoteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Other business methods--------------------------------------------------
}
