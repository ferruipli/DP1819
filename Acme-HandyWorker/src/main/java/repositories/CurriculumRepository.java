
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select case when (count(c)>0) then true else false end from Curriculum c where c.ticker = ?1")
	Boolean existCurriculumByTicker(String ticker);

	@Query("select c from Curriculum c")
	Collection<Curriculum> findAllCurriculums();

	@Query("select c.ticker from Curriculum c")
	Collection<String> findAllTickers();
}
