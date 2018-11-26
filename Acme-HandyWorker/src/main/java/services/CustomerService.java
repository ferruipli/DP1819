
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
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

	// Supporting services -------------------------
	@Autowired
	private BoxService			boxService;

	@Autowired
	private UtilityService		utilityService;


	// Constructors ----------------------------------
	public CustomerService() {
		super();
	}

	// Simple CRUD methods ---------------------------
	public Customer findOne(final int customerId) {
		Customer result;

		result = this.customerRepository.findOne(customerId);

		return result;
	}

	public Customer create() {
		Customer result;
		UserAccount userAccount;
		Authority role;
		List<Authority> authorities;

		role = new Authority();
		role.setAuthority(Authority.CUSTOMER);

		authorities = new ArrayList<Authority>();
		authorities.add(role);

		userAccount = new UserAccount();
		userAccount.setAuthorities(authorities);

		result = new Customer();
		result.setFixUpTasks(Collections.<FixUpTask> emptySet());
		result.setUserAccount(userAccount);

		return result;
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);
		this.utilityService.checkEmailActors(customer);

		Md5PasswordEncoder encoder;
		Customer result;
		String password, hash;

		encoder = new Md5PasswordEncoder();
		password = customer.getUserAccount().getPassword();
		hash = encoder.encodePassword(password, null);
		customer.getUserAccount().setPassword(hash);

		if (customer.getId() == 0) {
			result = this.customerRepository.save(customer);
			this.boxService.createDefaultBox(result);
		} else {
			this.checkByPrincipal(customer);

			result = this.customerRepository.save(customer);
		}

		return result;
	}

	// Other business methods ------------------------
	public Customer findCustomerByComplaint(final int reportId) {
		Customer result;

		result = this.customerRepository.findCustomerByReport(reportId);

		return result;
	}

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

	public void checkByPrincipal(final Customer customer) {
		Customer principal;

		principal = this.findByPrincipal();

		Assert.isTrue(customer.equals(principal));
	}

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount.getId());

		return result;
	}

	protected Customer findByUserAccount(final int userAccountId) {
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

	protected void removeFixUpTask(final Customer customer, final FixUpTask f) {
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = customer.getFixUpTasks();
		fixUpTasks.remove(f);

		customer.setFixUpTasks(fixUpTasks);
	}

}
