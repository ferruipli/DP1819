
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e where e.sender.id=?1")
	Page<Endorsement> findSentEndorsements(int senderId, Pageable pageable);

	@Query("select e from Endorsement e where e.recipient.id=?1")
	Page<Endorsement> findReceivedEndorsement(int recipientId, Pageable pageable);

	@Query("select e from Endorsement e where e.recipient.id=?1")
	Collection<Endorsement> findEndorsementsByEndorsable(int endorsableId);
}
