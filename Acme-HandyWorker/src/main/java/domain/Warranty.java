
package domain;

import org.hibernate.validator.constraints.NotBlank;

public class Warranty extends DomainEntity {

	// Constructor

	public Warranty() {
		super();
	}


	// Attributes

	private String	title;
	private String	terms;
	private boolean	finalMode;
	private String	laws;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	public boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final boolean finalMode) {
		this.finalMode = finalMode;
	}

	public String getLaws() {
		return this.laws;
	}

	public void setLaws(final String laws) {
		this.laws = laws;
	}

}
