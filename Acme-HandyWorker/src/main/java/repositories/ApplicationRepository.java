
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<domain.Application, Integer> {

	//Req 12.5.4 
	@Query("select avg(a.offeredPrice),min(a.offeredPrice),max(a.offeredPrice),sqrt(sum(a.offeredPrice * a.offeredPrice)/count(a.offeredPrice)-avg(a.offeredPrice)*avg(a.offeredPrice)) from Application a")
	Double[] findDataOfApplicationPrice();
	//Req 12.5.5 
	@Query("select (sum(case when a.status='PENDING' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioPendingApplications();
	//Req 12.5.6
	@Query("select (sum(case when a.status='ACCEPTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioAcceptedApplications();
	//Req 12.5.7 
	@Query("select (sum(case when a.status='REJECTED' then 1.0 else 0 end)/count(*)) from Application a")
	Double findRatioRejectedApplications();
	//Req 12.5.8
	@Query("select (sum(case when ((a.status='PENDING') and (a.registerMoment<CURRENT_TIMESTAMP))then 1.0 else 0 end)/count(*))from Application a")
	Double findRatioPendingApplicationsNotChangeStatus();

}
