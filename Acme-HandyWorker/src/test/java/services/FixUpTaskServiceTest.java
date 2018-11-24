
package services;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	// Service under test -----------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CustomerService		customerService;


	// Test -------------------------------------------------------------------

	// Test1: crear, guardar (y comprobar que está insertado en el customer correspondiente)
	// y borrar (y comprobar que no está insertado en el customer correspondiente) normalmente.
	// Test2: [PETE] actualizar con customer no correspondiente
	// Test3: [PETE] actualizar con applications
	// Test4: [PETE] eliminar con customer no correspondiente
	// Test5: [PETE] eliminar con applications

	@Test
	public void testSaveDeleteFixUpTask() {
		FixUpTask fixUpTask;
		Warranty warranty;
		Category category;
		Customer customer;

		category = this.categoryService.findOne(super.getEntityId("category1"));
		warranty = this.warrantyService.findOne(super.getEntityId("warranty1"));

		super.authenticate("customer1");

		customer = this.customerService.findByPrincipal();

		fixUpTask = this.fixUpTaskService.create();
		fixUpTask.setAddress("Direccion de test");
		fixUpTask.setCategory(category);
		fixUpTask.setCustomer(customer);
		fixUpTask.setDescription("Descripción de test");
		fixUpTask.setEndDate(LocalDate.now().plusYears(1).toDate());
		fixUpTask.setMaxPrice(5000.0);
		fixUpTask.setPublicationMoment(LocalDate.now().toDate());
		fixUpTask.setStartDate(LocalDate.now().plusMonths(1).toDate());
		fixUpTask.setWarranty(warranty);

		this.fixUpTaskService.save(fixUpTask);

		customer = this.customerService.findByPrincipal();
		Assert.isTrue(customer.getFixUpTasks().contains(fixUpTask));

		this.fixUpTaskService.delete(fixUpTask);
		customer = this.customerService.findByPrincipal();
		Assert.isTrue(!customer.getFixUpTasks().contains(fixUpTask));

		super.unauthenticate();
	}

}
