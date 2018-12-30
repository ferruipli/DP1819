
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

	@Query(value = "select e from EducationRecord e where e.id in (select ee.id from Curriculum c join c.educationRecords ee where c.id = ?1)", countQuery = "select count(e) from Curriculum c join c.educationRecords e where c.id = ?1")
	Page<EducationRecord> findEducationRecordByCurriculum(int CurriculumId, Pageable pageable);

}
