
package domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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
	private String	category;
	private String	warranty;


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

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	@Pattern(regexp = "[a-zA-Z]+")
	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	@NotBlank
	@Pattern(regexp = "[a-zA-Z]+")
	public String getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final String warranty) {
		this.warranty = warranty;
	}
}
