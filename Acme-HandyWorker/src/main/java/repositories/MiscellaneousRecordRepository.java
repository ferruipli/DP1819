
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

	@Query(value = "select m from MiscellaneousRecord m where m.id in (select mm.id from Curriculum c join c.miscellaneousRecords mm where c.id = ?1)", countQuery = "select count(m) from Curriculum c join c.miscellaneousRecords m where c.id = ?1")
	Page<MiscellaneousRecord> findMiscellaneousRecordByCurriculum(int id, Pageable pageable);

}
