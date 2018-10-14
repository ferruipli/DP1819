
package domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import security.UserAccount;

@Entity
public class Actor extends DomainEntity {

	// Constructors

	public Actor() {
		super();
	}


	// Attributes

	// Relationships
	private Collection<Shout>	shouts;
	private UserAccount			userAccount;


	@NotNull
	@OneToMany(mappedBy = "creator")
	public Collection<Shout> getShouts() {
		return this.shouts;
	}

	public void setShouts(final Collection<Shout> shouts) {
		this.shouts = shouts;
	}

	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}
