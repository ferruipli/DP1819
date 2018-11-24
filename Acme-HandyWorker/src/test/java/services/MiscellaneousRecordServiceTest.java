
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

}
