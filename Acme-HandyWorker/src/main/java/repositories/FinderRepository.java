
package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	Page<FixUpTask> findFixUpTaskByStartPrice(Double startPrice, Pageable pageable);
	//TODO
	//	@Query("select f from FixUpTask f where (f.address like concat('%', concat(?1, '%')))or(f.ticker like concat('%', concat(?1, '%'))) or (f.description like concat('%', concat(?1, '%')))) ")
	//	Page<FixUpTask> findFixUpTaskByKeyword(String keyword, Pageable pageable);
	//
	//	@Query("")
	//	Page<FixUpTask> findFixUpTaskByStartPrice(Double startPrice, Double endPrice, Pageable pageable);
	//
	//	@Query("select f from FixUpTask f where f.maxPrice<=?1")
	//	Page<FixUpTask> findFixUpTaskByEndPrice(Double endPrice, Pageable pageable);
	//
	//	@Query("select f from FixUpTask f where f.startDate<=?1")
	//	Page<FixUpTask> findFixUpTaskByStartDate(Date startDate, Pageable pageable);
	//
	//	@Query("select f from FixUpTask f where f.startDate>=?1")
	//	Page<FixUpTask> findFixUpTaskByEndDate(Date endDate, Pageable pageable);
	//
	//	@Query("select f from FixUpTask f where f.warranty.title = ?1")
	//	Page<FixUpTask> findFixUpTaskByWarranty(String warranty, Pageable pageable);

	@Query("select f from FixUpTask f join f.category c join c.categoriesTranslations ct where ((f.address like concat('%', concat(?1, '%'))) or (f.ticker like concat('%', concat(?1, '%'))) or (f.description like concat('%', concat(?1, '%')))) and (f.maxPrice between ?2 and ?3) and (f.startDate between ?4 and ?5)  and (f.endDate between ?4 and ?5)   and (f.warranty.title like ?6)and ( ct.name = ?7))")
	Page<FixUpTask> findFixUpTaskFinder(String keyWord, Double startPrice, Double endPrice, Date startDate, Date endDate, String warranty, String category, Pageable pageable);
}
