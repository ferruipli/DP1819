
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Customer extends Actor {

	// Constructors

	public Customer() {
		super();
	}


	// Relationships

	private Collection<Shout>	shouts;


	@NotNull
	@OneToMany(mappedBy = "customer")
	public Collection<Shout> getShouts() {
		return this.shouts;
	}

	public void setShouts(final Collection<Shout> shouts) {
		this.shouts = shouts;
	}

}
