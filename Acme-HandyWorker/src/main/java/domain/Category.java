
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
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


	// Relationships ----------------------------------------------------------

	private Category				parent;
	private Collection<Category>	descendants;


	@Valid
	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

	@NotNull
	@OneToMany(mappedBy = "parent")
	public Collection<Category> getDescendants() {
		return this.descendants;
	}

	public void setDescendants(final Collection<Category> descendants) {
		this.descendants = descendants;
	}
}
