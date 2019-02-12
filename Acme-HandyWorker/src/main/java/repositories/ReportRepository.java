
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r join r.notes n where n.id = ?1")
	Report findByNoteId(int noteId);

	@Query("select r.id from Report r join r.notes n where n.id = ?1")
	Integer findIdByNoteId(int noteId);

}
