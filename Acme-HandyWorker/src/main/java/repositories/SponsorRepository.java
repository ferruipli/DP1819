
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	@Query("select s from Sponsor s where s.userAccount.id = ?1")
	Sponsor findByUserAccountId(int id);

	@Query("select s from Sponsor s join s.sponsorships s1 where s1.id=?1")
	Sponsor findSponsorBySponsorshipId(int id);

}
