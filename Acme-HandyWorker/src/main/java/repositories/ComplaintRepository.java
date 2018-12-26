
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c.id from Complaint c where c.report.id = ?1")
	Integer findIdByReportId(int reportId);

	@Query("select c from Referee r join r.complaints c where c.id = ?1")
	Complaint findAssigned(int complaintId);

	@Query("select c from Complaint c where c.fixUpTask.id = ?1")
	Page<Complaint> findByFixUpTaskId(int fixUpTaskId, Pageable pageable);

	@Query("select c from Complaint c where c not in (select c2 from Referee r join r.complaints c2)")
	Page<Complaint> findNotAssigned(Pageable pageable);

	@Query("select c from HandyWorker hw join hw.applications a join a.fixUpTask.complaints c where hw.id = ?1 and a.status = 'ACCEPTED'")
	Page<Complaint> findInvolvedByHandyWorkerId(int handyWorkerId, Pageable pageable);

	@Query("select c from Complaint c where c.report.id = ?1")
	Complaint findByReportId(int reportId);

	@Query("select c from Referee r join r.complaints c where r.id = ?1")
	Page<Complaint> findSelfAssignedByPrincipal(int principalId, Pageable pageable);

	@Query("select c.ticker from Complaint c where c.ticker = ?1")
	String existTicker(String ticker);

}
