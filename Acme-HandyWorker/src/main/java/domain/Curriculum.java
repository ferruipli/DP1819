
package domain;

import javax.validation.constraints.Pattern;

public class Curriculum extends DomainEntity {

	// Constructors

	public Curriculum() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	ticker;


	@Pattern(regexp = "\\d{6}-[A-Z0-9]{6}")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

}
