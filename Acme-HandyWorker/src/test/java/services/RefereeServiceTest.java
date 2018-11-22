
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;
import utilities.AbstractTest;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private RefereeService			refereeService;

	// Supporting repositories ------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Test -------------------------------------------------------------------

	// TODO: Esperar a que acabe Complaint
	// complaint1 = coger un complaint para añadirselo al referee guardado 
	// y comprobar que se actualiza la base de datos al añadir otro

	@Test
	public void testSaveReferee() {
		Referee referee, saved;
		UserAccount userAccount;

		userAccount = this.userAccountRepository.findByUsername("referee1");

		referee = this.refereeService.create();
		referee.setName("Juan");
		referee.setAddress("Calle Test");
		referee.setEmail("juan <juantest@gmail.com>");
		referee.setMiddleName("Master");
		referee.setPhoneNumber("+42 123123123");
		referee.setPhotoLink("hhtps://imagetest.com/12g5hr45");
		referee.setSurname("López");
		referee.setUserAccount(userAccount);

		saved = this.refereeService.save(referee);
		Assert.isTrue(this.refereeService.findOne(saved.getId()).equals(saved));
	}
}
