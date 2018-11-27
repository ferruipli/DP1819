
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;
import domain.Phase;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f where ?1 member of f.phases")
	FixUpTask findByPhase(Phase phase);

	@Query("select a.fixUpTask from HandyWorker hw join hw.applications a where hw.id = ?1 and a.status = 'ACCEPTED'")
	Collection<FixUpTask> findWorkableFixUpTasks(int handyWorkerId);

}
