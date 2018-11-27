
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

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size)/ count(c.fixUpTasks.size) -avg(c.fixUpTasks.size)*avg(c.fixUpTasks.size)) from Customer c")
	double[] findDataNumberFixUpTaskPerUser();

	@Query("select avg(f.maxPrice), min(f.maxPrice),max(f.maxPrice), sqrt(sum(f.maxPrice*f.maxPrice)/count(f.maxPrice)- avg(f.maxPrice)*avg(f.maxPrice)) from FixUpTask f")
	double[] findDataMaximumPrice();

	@Query("select count(c)/(select count(f) from FixUpTask f)*1.0 from FixUpTask c where c.complaints.size=1")
	double findRatioFixUpTaskWithComplaint();

}
