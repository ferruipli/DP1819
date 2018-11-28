
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.CreditCard;
import domain.Sponsorship;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Query("select a from Application a where a.creditCard = ?1")
	Collection<Application> findApplicationByCreditCard(int id);

	@Query("select s from Sponsorship s where s.creditCard.id = ?1")
	Collection<Sponsorship> findSponsorshipByCreditCard(int id);

}
