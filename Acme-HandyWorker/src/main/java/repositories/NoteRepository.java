
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), sqrt(sum (r.notes.size * r.notes.size) / count (r.notes.size)- avg(r.notes.size) *avg(r.notes.size)) from Report r")
	double[] findDataNumberNotesPerReport();

}
