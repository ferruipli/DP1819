
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer> {

	@Query(value = "select p from ProfessionalRecord p where p.id in (select pp.id from Curriculum c join c.professionalRecords pp where c.id = ?1)", countQuery = "select count(p) from Curriculum c join c.professionalRecords p where c.id = ?1")
	Page<ProfessionalRecord> findProfessionalRecordByCurriculum(int id, Pageable pageable);

}
