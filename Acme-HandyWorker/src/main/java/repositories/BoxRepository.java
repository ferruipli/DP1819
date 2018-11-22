
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b.name from Box b join b.actor a where a.id =?2 and b.name=?1")
	Collection<String> existNameboxForActor(String name, int actorId);

}
