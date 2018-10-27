
package domain;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class Money {

	// Attributes

	private Double	amount;
	private String	currency;


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	@NotBlank
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

}
