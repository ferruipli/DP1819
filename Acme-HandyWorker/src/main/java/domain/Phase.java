
package domain;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class Phase extends DomainEntity {

	// Constructor

	public Phase() {
		super();
	}


	// Attributes

	private Integer	number;
	private String	title;
	private String	description;
	private Date	startMoment;
	private Date	endMoment;


	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	public Date getEndMoment() {
		return this.endMoment;
	}

	public void setEndMoment(final Date endMoment) {
		this.endMoment = endMoment;
	}

}
