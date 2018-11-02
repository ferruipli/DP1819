
package domain;

import org.hibernate.validator.constraints.NotBlank;

public class Category extends DomainEntity {

	// Constructors

	public Category() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	name;
	private String	language;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

}
