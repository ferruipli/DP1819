
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Tutorial extends DomainEntity {

	// Constructor

	public Tutorial() {
		super();
	}


	// Attributes

	private String	title;
	private Date	moment;
	private String	summary;
	private String	pictures;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@URL
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}


	//Relationship--------------------------------------------
	private Collection<Sponsorship>	sponsorShips;


	@Valid
	@NotNull
	@OneToMany
	public Collection<Sponsorship> getSponsorShips() {
		return this.sponsorShips;
	}

	public void setSponsorShips(final Collection<Sponsorship> sponsorShips) {
		this.sponsorShips = sponsorShips;
	}

}
