
package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select f from FixUpTask f join f.phases ph where ph.id = ?1")
	FixUpTask findByPhaseId(int phaseId);

	@Query("select f.id from FixUpTask f join f.phases ph where ph.id = ?1")
	Integer findIdByPhaseId(int phaseId);

	@Query("select a.fixUpTask from HandyWorker hw join hw.applications a where hw.id = ?2 and a.status = 'ACCEPTED' and a.fixUpTask.id = ?1")
	FixUpTask findWorkableFixUpTask(int fixUpTaskId, int handyWorkerId);

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size)/ count(c.fixUpTasks.size) -avg(c.fixUpTasks.size)*avg(c.fixUpTasks.size)) from Customer c")
	double[] findDataNumberFixUpTaskPerUser();

	@Query("select avg(f.maxPrice), min(f.maxPrice),max(f.maxPrice), sqrt(sum(f.maxPrice*f.maxPrice)/count(f.maxPrice)- avg(f.maxPrice)*avg(f.maxPrice)) from FixUpTask f")
	double[] findDataMaximumPrice();

	@Query("select count(c)/(select count(f) from FixUpTask f)*1.0 from FixUpTask c where c.complaints.size=1")
	double findRatioFixUpTaskWithComplaint();

	@Query("select f from FixUpTask f join f.category c join c.categoriesTranslations ct where (((f.address like concat('%', concat(?1, '%'))) or (f.ticker like concat('%', concat(?1, '%'))) or (f.description like concat('%', concat(?1, '%')))) and (f.maxPrice between ?2 and ?3) and (f.startDate between ?4 and ?5)  and (f.endDate between ?4 and ?5) and (f.warranty.title like concat('%', concat(?6, '%'))) and (ct.name like concat('%', concat(?7, '%'))) )")
	Page<FixUpTask> findFixUpTaskFinder(String keyWord, Double startPrice, Double endPrice, Date startDate, Date endDate, String warranty, String category, Pageable pageable);

	@Query("select f.ticker from FixUpTask f where f.ticker = ?1")
	String existTicker(String ticker);
}
