
package domain;

import org.hibernate.validator.constraints.NotBlank;

public class Box extends DomainEntity {

	// Constructors

	public Box() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	name;
	private Boolean	isSystemBox;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Boolean getIsSystemBox() {
		return this.isSystemBox;
	}

	public void setIsSystemBox(final Boolean isSystemBox) {
		this.isSystemBox = isSystemBox;
	}

}
