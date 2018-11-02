
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Constructors

	public Box() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	name;
	private boolean	isSystemBox;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean getIsSystemBox() {
		return this.isSystemBox;
	}

	public void setIsSystemBox(final boolean isSystemBox) {
		this.isSystemBox = isSystemBox;
	}


	// Relationship----------------------------------------------------------------
	private Collection<Message>	messages;


	@Valid
	@NotNull
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

}
