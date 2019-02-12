
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsable;

@Repository
public interface EndorsableRepository extends JpaRepository<Endorsable, Integer> {

	@Query("select e from Endorsable e where e.userAccount.id=?1")
	Endorsable findByUserAccount(int userAccountId);

	@Query("select distinct e.recipient from Endorsement e")
	Collection<Endorsable> findEndorsableWithReceivedEndorsements();
}
