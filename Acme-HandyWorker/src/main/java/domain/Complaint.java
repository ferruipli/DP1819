
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends DomainEntity {

	// Constructors

	public Complaint() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	ticker;
	private Date	moment;
	private String	description;
	private String	attachments;


	@Pattern(regexp = "\\d{6}-[A-Z0-9]{6}")
	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	public String getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}


	// Relationships ----------------------------------------------------------

	private Report		report;
	private FixUpTask	fisxUpTask;


	@NotNull
	@Valid
	@OneToOne(optional = true)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public FixUpTask getFisxUpTask() {
		return this.fisxUpTask;
	}

	public void setFisxUpTask(final FixUpTask fisxUpTask) {
		this.fisxUpTask = fisxUpTask;
	}
}
