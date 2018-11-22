
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Integer> {

	// COMPLT: Query para obtener las fases ordenadas por su número dado un id de un FixUpTask

}
