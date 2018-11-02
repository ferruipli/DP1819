
package domain;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class CreditCard extends DomainEntity {

	// Attributes ----------------------------------------------------------------------

	private String	holderName;
	private String	brandName;
	private String	number;
	private String	expirationMonth;
	private String	expirationYear;
	private int		cvvCode;


	@NotBlank
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	@CreditCardNumber
	@NotBlank
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	@NotBlank
	@Range(min = 1, max = 12)
	public String getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	@NotBlank
	@Digits(integer = 2, fraction = 0)
	public String getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final String expirationYear) {
		this.expirationYear = expirationYear;
	}

	@Range(min = 100, max = 999)
	public int getCvvCode() {
		return this.cvvCode;
	}

	public void setCvvCode(final int cvvCode) {
		this.cvvCode = cvvCode;
	}

}
