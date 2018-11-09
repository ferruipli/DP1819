
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class CategoryTranslation extends DomainEntity {

	// Constructors
	public CategoryTranslation() {
		super();
	}


	// Attributes -----------------------------------------
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
