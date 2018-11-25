
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
import domain.Endorsable;
import domain.Endorsement;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// Services under testing ---------------------------------------
	@Autowired
	private EndorsementService	endorsementService;

	// Supporting services ------------------------------------------
	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private EndorsableService	endorsableService;


	// Test ---------------------------------------------------------
	@Test
	public void testPlayedRole() {
		super.authenticate("customer1");

		final int id = super.getEntityId("handyworker1");
		boolean res;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(id);

		res = this.endorsementService.playedRole(handyWorker, "HANDYWORKER");

		Assert.isTrue(res);

		super.unauthenticate();
	}

	@Test
	public void testFindSentEndorsements() {
		super.authenticate("customer1");

		Collection<Endorsement> all;
		final int id = super.getEntityId("customer1");

		all = this.endorsementService.findSentEndorsements();

		Assert.notNull(all);
		Assert.isTrue(all.size() > 0);

		super.unauthenticate();
	}

	@Test
	public void testFindReceivedEndorsements() {
		super.authenticate("customer1");
		final int id = super.getEntityId("customer1");

		Collection<Endorsement> all;

		all = this.endorsementService.findReceivedEndorsements();

		Assert.notNull(all);
		Assert.isTrue(all.size() > 0);

		super.unauthenticate();
	}

	@Test
	public void testCreate() {
		super.authenticate("customer6");

		Endorsable endorsable;
		Endorsement endorsement;

		endorsable = this.endorsableService.findByPrincipal();
		endorsement = this.endorsementService.create();

		Assert.notNull(endorsement);
		Assert.isNull(endorsement.getRecipient());
		Assert.isNull(endorsement.getMoment());
		Assert.isNull(endorsement.getComments());
		Assert.isTrue(endorsement.getSender().equals(endorsable));

		super.unauthenticate();
	}

	@Test
	public void positiveTestSave_uno() {
		super.authenticate("customer6");

		Collection<Endorsement> all;
		Endorsable recipient;
		Endorsement endorsement, saved;
		int recipientId;

		recipientId = super.getEntityId("handyworker6");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		saved = this.endorsementService.save(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(all.contains(saved));

		super.unauthenticate();
	}

	@Test
	public void positiveTestSave_dos() {
		super.authenticate("handyworker5");

		Collection<Endorsement> all;
		Endorsable recipient;
		Endorsement endorsement, saved;
		int recipientId;

		recipientId = super.getEntityId("customer5");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		saved = this.endorsementService.save(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(all.contains(saved));

		super.unauthenticate();
	}

	/* Test negativo: endorsement = null */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_uno() {
		super.authenticate("customer6");

		Endorsement endorsement, saved;

		endorsement = null;

		saved = this.endorsementService.save(endorsement);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/* Test negativo: el sender no coincide con el principal */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_dos() {
		super.authenticate("customer6");

		int intruderId, recipientId;
		Endorsable intruder, recipient;
		Endorsement endorsement, saved;

		intruderId = super.getEntityId("handyWorker3");
		recipientId = super.getEntityId("handyWorker6");

		intruder = this.handyWorkerService.findOne(intruderId);
		recipient = this.handyWorkerService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setSender(intruder);
		endorsement.setRecipient(recipient);
		endorsement.setComments("Sin verguenza!!");

		saved = this.endorsementService.save(endorsement);

		Assert.isNull(saved);

		super.unauthenticate();
	}

	/*
	 * Test negativo: el customer va a realizar una recomendacion
	 * sobre un handyWorker que no ha trabajado con el
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_tres() {
		super.authenticate("customer3");

		Collection<Endorsement> all;
		Endorsable recipient;
		Endorsement endorsement, saved;
		int recipientId;

		recipientId = super.getEntityId("handyWorker3");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		saved = this.endorsementService.save(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/*
	 * Test negativo: El handyworker6 no ha trabajado para dicho
	 * customer3 y va a realizarle una recomendacion
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_cuatro() {
		super.authenticate("handyworker6");

		Collection<Endorsement> all;
		Endorsable recipient;
		Endorsement endorsement, saved;
		int recipientId;

		recipientId = super.getEntityId("customer3");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		saved = this.endorsementService.save(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/*
	 * El handyworker6 solo ha trabajado para el customer6
	 * pero quiere hacer una recomendacion sobre el customer1
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_cinco() {
		super.authenticate("handyworker6");

		Collection<Endorsement> all;
		Endorsable recipient;
		Endorsement endorsement, saved;
		int recipientId;

		recipientId = super.getEntityId("customer1");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		saved = this.endorsementService.save(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/*
	 * Test negativo: el customer2 puede crear una recomendacion
	 * sobre los handyworkers 2 y 3 pero se la hace al
	 * handyworker 5
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_seis() {
		super.authenticate("customer2");

		Collection<Endorsement> all;
		Endorsable recipient;
		Endorsement endorsement, saved;
		int recipientId;

		recipientId = super.getEntityId("handyworker5");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		saved = this.endorsementService.save(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(!all.contains(saved));

		super.unauthenticate();
	}

	/* Test negativo: endorsement null */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestDelete_uno() {
		super.authenticate("customer2");

		Collection<Endorsement> all;
		Endorsement endorsement;

		endorsement = null;

		this.endorsementService.delete(endorsement);

		all = this.endorsementService.findSentEndorsements();

		Assert.isTrue(all.contains(endorsement));

		super.unauthenticate();
	}

	/* Test negativo: endorsement que no se encuentra en la BD */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestDelete_dos() {
		super.authenticate("customer1");

		Endorsement endorsement, deleted;
		Endorsable recipient;
		int recipientId;

		recipientId = super.getEntityId("customer5");
		recipient = this.endorsableService.findOne(recipientId);

		endorsement = this.endorsementService.create();
		endorsement.setRecipient(recipient);
		endorsement.setComments("Gran trabajador, mejor persona.");

		this.endorsementService.delete(endorsement);

		deleted = this.endorsementService.findOne(endorsement.getId());

		Assert.isNull(deleted);

		super.unauthenticate();
	}

	/*
	 * Test negativo: customer3 tratar de borrar una recomendacion
	 * que no ha escrito el o ella.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestDelete_tres() {
		super.authenticate("customer3");

		Endorsement endorsement, e;
		int id;

		id = super.getEntityId("endorsement1");
		endorsement = this.endorsementService.findOne(id);

		this.endorsementService.delete(endorsement);

		e = this.endorsementService.findOne(id);

		Assert.notNull(e);

		super.unauthenticate();
	}

	@Test
	public void positiveTestDelete() {
		super.authenticate("customer1");

		Endorsement endorsement, e;
		int id;

		id = super.getEntityId("endorsement1");
		endorsement = this.endorsementService.findOne(id);

		this.endorsementService.delete(endorsement);

		e = this.endorsementService.findOne(id);

		Assert.isNull(e);

		super.unauthenticate();
	}

}
