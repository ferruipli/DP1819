
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select r from Referee r where r.userAccount.id=?1")
	Referee findByUserAccount(int userAccountId);

	@Query("select r from Referee r join r.complaints c where c.report.id = ?1")
	Referee findByReportId(int reportId);

	@Query("select r from Referee r join r.complaints c where c.id = ?1")
	Referee findHandlerByComplaintId(int complaintId);

}
