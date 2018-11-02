
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

	private Category				category;
	private Collection<Category>	categories;


	@NotNull
	@Valid
	@ManyToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@NotNull
	@OneToMany(mappedBy = "Category")
	public Collection<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(final Collection<Category> categories) {
		this.categories = categories;
	}
}
