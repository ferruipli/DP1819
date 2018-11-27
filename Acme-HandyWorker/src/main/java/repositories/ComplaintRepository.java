
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c from Complaint c where c.fixUpTask.customer.id = ?1")
	Collection<Complaint> findByCustomerPrincipal(int customerId);

	@Query("select c from Complaint c where c not in (select c2 from Referee r join r.complaints c2)")
	Collection<Complaint> findNotSelfAssigned();

	@Query("select c from HandyWorker hw join hw.applications a join a.fixUpTask.complaints c where hw.id = ?1 and a.status = 'ACCEPTED'")
	Collection<Complaint> findInvolvedByHandyWorkerId(int handyWorkerId);

	@Query("select c from Complaint c where c.report.id = ?1")
	Complaint findByReportId(int reportId);

}
