
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f join f.phases ph where ph.id = ?1")
	FixUpTask findByPhaseId(int phaseId);

	@Query("select a.fixUpTask from HandyWorker hw join hw.applications a where hw.id = ?1 and a.status = 'ACCEPTED'")
	Collection<FixUpTask> findWorkableFixUpTasks(int handyWorkerId);

}
