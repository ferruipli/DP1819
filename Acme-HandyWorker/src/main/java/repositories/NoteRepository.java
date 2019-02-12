
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), sqrt(sum (r.notes.size * r.notes.size) / count (r.notes.size)- avg(r.notes.size) *avg(r.notes.size)) from Report r")
	Double[] findDataNumberNotesPerReport();

	@Query(value = "select n from Note n where n.id in (select nn.id from Report r join r.notes nn where r.id = ?1)", countQuery = "select count(n) from Report r join r.notes n where r.id = ?1")
	Page<Note> findByReportId(final int reportId, final Pageable pageable);

}
