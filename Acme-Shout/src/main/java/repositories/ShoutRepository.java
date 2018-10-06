
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Shout;

@Repository
public interface ShoutRepository extends JpaRepository<Shout, Integer> {

}
