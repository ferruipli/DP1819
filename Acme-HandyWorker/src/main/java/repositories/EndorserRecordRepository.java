
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

	@Query(value = "select e from EndorserRecord e where e.id in (select ee.id from Curriculum c join c.endorserRecords ee where c.id = ?1)", countQuery = "select count(e) from Curriculum c join c.endorserRecords e where c.id = ?1")
	Page<EndorserRecord> findEndorserRecordByCurriculum(int id, Pageable pageable);

}
