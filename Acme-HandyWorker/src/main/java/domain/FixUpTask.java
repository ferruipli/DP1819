
package domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class FixUpTask extends DomainEntity {

	// Constructors

	private FixUpTask() {
		super();
	}


	// Atributes

	private String	ticker;
	private Date	publicationMoment;
	private String	description;
	private String	address;
	private Money	maxPrice;
	private Date	startDate;
	private Date	endDate;


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
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	@Valid
	@NotNull
	public Money getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Money maxPrice) {
		this.maxPrice = maxPrice;
	}

	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

}
