
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Box b join b.actor a where b.name=?2 and a.id=?1")
	Box searchBox(int actorId, String nameBox);

	@Query("select b from Box b join b.actor a where a.id=?1")
	Collection<Box> findAllBoxByActor(int actorId);

	@Query("select b from Box b join b.messages m where m.id=?1")
	Collection<Box> boxWithMessage(int messageId);

}
