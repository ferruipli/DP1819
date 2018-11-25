
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;
import domain.Phase;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select h from HandyWorker h where h.userAccount.id = ?1")
	HandyWorker findByUserAccount(int id);

	@Query("select distinct h from HandyWorker h join h.applications a where a.status='ACCEPTED' and a.fixUpTask.customer.id=?1")
	Collection<HandyWorker> findEndorsableHandyWorkers(int customerId);

	@Query("select a.handyWorker.id from FixUpTask f join f.applications a where ?1 member of f.phases and a.status = 'ACCEPTED'")
	int findPhaseCreatorId(Phase phase);

	@Query("select a.handyWorker from FixUpTask f join f.complaints com join f.applications a where a.status = 'ACCEPTED' AND com.report.id = ?1")
	HandyWorker findByReportId(int reportId);

}
