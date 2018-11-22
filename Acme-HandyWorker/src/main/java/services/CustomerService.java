
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import repositories.CustomerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	// Managed repository ----------------------------
	@Autowired
	private CustomerRepository	customerRepository;


	// Supporting repository -------------------------

	// Constructors ----------------------------------
	public CustomerService() {
		super();
	}

	// Simple CRUD methods ---------------------------

	// Other business methods ------------------------

	public Collection<Customer> topThreeCustomer() {
		final Collection<Customer> results;
		Page<Customer> customers;
		Pageable pageable;

		pageable = new PageRequest(0, 3);
		customers = this.customerRepository.topThreeCustomer(pageable);

		results = customers.getContent();

		return results;
	}

	public Collection<Customer> customerMoreThanAverage() {
		Collection<Customer> results;

		results = this.customerRepository.customerMoreThanAverage();

		return results;
	}

	public Collection<Customer> findEndorsableCustomers(final int handyWorkerId) {
		Collection<Customer> results;

		results = this.customerRepository.findEndorsableCustomers(handyWorkerId);

		return results;
	}

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	private Customer findByUserAccount(final int userAccountId) {
		Customer result;

		result = this.customerRepository.findByUserAccount(userAccountId);

		return result;
	}

	protected void addFixUpTask(final Customer customer, final FixUpTask f) {
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = customer.getFixUpTasks();
		fixUpTasks.add(f);

		customer.setFixUpTasks(fixUpTasks);
	}

}
