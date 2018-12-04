
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select c.ticker from Curriculum c")
	Collection<String> findAllTickers();

	@Query("select c.ticker from Curriculum c where c.ticker = ?1")
	String existTicker(String ticker);

	@Query("select (count(c.ticker)>0) from Curriculum c where c.ticker = ?1")
	boolean existTickerBd(String ticker);
}
