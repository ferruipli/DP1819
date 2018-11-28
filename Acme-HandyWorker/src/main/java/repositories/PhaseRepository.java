
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	@Query("select ph from FixUpTask f join f.phases ph where f.id = ?1 order by ph.startMoment")
	Collection<Phase> findByFixUpTaskIdOrdered(int fixUpTaskId);

}
