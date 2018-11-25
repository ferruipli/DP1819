
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select c.report from Referee ref join ref.complaints c where ref.id = ?1")
	Collection<Report> findByRefereeId(int refereeId);

	@Query("select r from Report r join r.notes n where n.id = ?1")
	Report findByNoteId(int noteId);

}
