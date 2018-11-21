
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	@Query("select case when (count(c)>0) then true else false end from Curriculum c where c.ticker = ?1")
	Boolean existCurriculum(String ticker);

}
