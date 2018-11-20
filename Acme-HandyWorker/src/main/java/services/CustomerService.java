
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import repositories.CustomerRepository;
import domain.Customer;

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

}
