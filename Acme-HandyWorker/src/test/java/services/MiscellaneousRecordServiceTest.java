
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	//Service under test ----------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	//Test ------------------------------------------------

	@Test
	public void testCreateMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();

		Assert.notNull(miscellaneousRecord);
		Assert.isNull(miscellaneousRecord.getTitle());
		Assert.isNull(miscellaneousRecord.getAttachment());
		Assert.isNull(miscellaneousRecord.getComments());

	}

	@Test
	public void testSaveMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord, saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		String title, attachment, comments;

		super.authenticate("handyworker1");

		miscellaneousRecord = this.miscellaneousRecordService.create();

		title = "miscellaneous record 1";
		attachment = "http://www.google.com";
		comments = "comment";

		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setComments(comments);

		saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(saved));

		super.authenticate(null);
	}

	// Guardar un miscellaneous record sin estar autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testSaveMiscellaneousRecordNegative() {
		MiscellaneousRecord miscellaneousRecord, saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		String title, attachment, comments;

		miscellaneousRecord = this.miscellaneousRecordService.create();

		title = "miscellaneous record 1";
		attachment = "http://www.google.com";
		comments = "comment";

		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setComments(comments);

		saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(saved));

	}

	// Guardar un miscellaneous record sin ser un handyworker
	@Test(expected = NullPointerException.class)
	public void testSaveMiscellaneousRecordNegative2() {
		MiscellaneousRecord miscellaneousRecord, saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		String title, attachment, comments;

		super.authenticate("customer1");

		miscellaneousRecord = this.miscellaneousRecordService.create();

		title = "miscellaneous record 1";
		attachment = "http://www.google.com";
		comments = "comment";

		miscellaneousRecord.setTitle(title);
		miscellaneousRecord.setAttachment(attachment);
		miscellaneousRecord.setComments(comments);

		saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testUpdateMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;
		int id;
		String newTitle, lastTitle;

		id = super.getEntityId("miscellaneousRecord1");
		newTitle = "Nuevo title ";

		miscellaneousRecord = this.miscellaneousRecordService.findOne(id);
		lastTitle = miscellaneousRecord.getTitle();

		miscellaneousRecord.setTitle(newTitle);

		this.miscellaneousRecordService.save(miscellaneousRecord);

		Assert.isTrue(!lastTitle.equals(miscellaneousRecord.getTitle()));

	}

	@Test
	public void testDeleteMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		super.authenticate("handyworker1");

		miscellaneousRecord = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));
		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(miscellaneousRecord));

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(!(miscellaneousRecords.contains(miscellaneousRecord)));

		super.authenticate(null);
	}

	//Eliminar un miscellaneous record sin estar autenticado
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteMiscellaneousRecordNegative() {
		MiscellaneousRecord miscellaneousRecord;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));
		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(miscellaneousRecord));

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(!(miscellaneousRecords.contains(miscellaneousRecord)));

	}

	// Eliminar un miscellaneous record sin ser un handyworker
	@Test(expected = NullPointerException.class)
	public void testDeleteMiscellaneousRecordNegative2() {
		MiscellaneousRecord miscellaneousRecord;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		super.authenticate("customer1");

		miscellaneousRecord = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));
		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(miscellaneousRecord));

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(!(miscellaneousRecords.contains(miscellaneousRecord)));

		super.authenticate(null);
	}

}
