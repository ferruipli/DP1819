
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Services under testing ---------------------------
	@Autowired
	private CustomerService	customerService;


	// Supporting services ------------------------------

	// Test ---------------------------------------------

	@Test
	public void testTopThreeCustomer() {
		super.authenticate("admin1");

		Collection<Customer> customers;

		customers = this.customerService.topThreeCustomer();

		Assert.notNull(customers);
		Assert.isTrue(customers.size() == 3);

		super.unauthenticate();
	}

	@Test
	public void testCustomerMoreThanAverage() {
		super.authenticate("admin1");

		Collection<Customer> customers;

		customers = this.customerService.topThreeCustomer();

		Assert.notNull(customers);
		Assert.isTrue(customers.size() > 0);

		super.unauthenticate();
	}

}
