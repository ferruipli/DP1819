
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Box b join b.actor a where b.name=?2 and a.id=?1")
	Box searchBox(int idUA, String string);

}
