
package domain;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

public class Finder extends DomainEntity {

	// Constructor

	public Finder() {
		super();
	}


	// Attributes

	private String	keyword;
	private Money	startPrice;
	private Money	endPrice;
	private Date	startDate;
	private Date	endDate;


	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	@Valid
	public Money getStartPrice() {
		return this.startPrice;
	}

	public void setStartPrice(final Money startPrice) {
		this.startPrice = startPrice;
	}

	@Valid
	public Money getEndPrice() {
		return this.endPrice;
	}

	public void setEndPrice(final Money endPrice) {
		this.endPrice = endPrice;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}
}
