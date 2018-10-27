
package domain;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

public class Curriculum extends DomainEntity {

	// Constructors

	public Curriculum() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	ticker;


	@Column(unique = true)
	@Pattern(regexp = "\\d{6}-[A-Z0-9]{6}")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

}
