
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

	@Query("select f from FixUpTask f where (f.ticker like ?1 or f.description like ?1 or f.address like ?1)")
	Page<FixUpTask> findFixUpTaskByKeyword(String keyword, Pageable pageable);

	@Query("select f from FixUpTask f where f.maxPrice>=?1")
	Page<FixUpTask> findFixUpTaskByStartPrice(Double startPrice, Pageable pageable);

	@Query("select f from FixUpTask f where f.maxPrice<=?1")
	Page<FixUpTask> findFixUpTaskByEndPrice(Double endPrice, Pageable pageable);

	@Query("select f from FixUpTask f where f.startDate<=?1")
	Page<FixUpTask> findFixUpTaskByStartDate(Date startDate, Pageable pageable);

	@Query("select f from FixUpTask f where f.startDate>=?1")
	Page<FixUpTask> findFixUpTaskByEndDate(Date endDate, Pageable pageable);

	//TODO
	//@Query("select f from FixUpTask f where f.category. like ?1")
	//Page<FixUpTask> findFixUpTaskByCategory(String category, Pageable pageable);

	@Query("select f from FixUpTask f where f.warranty.title like ?1")
	Page<FixUpTask> findFixUpTaskByWarranty(String warranty, Pageable pageable);
}
