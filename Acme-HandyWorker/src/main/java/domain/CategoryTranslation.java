
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {
	"name", "language"
}))
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
