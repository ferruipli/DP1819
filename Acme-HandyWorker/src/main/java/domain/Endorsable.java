
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Endorsable extends Actor {

	// Constructor

	public Endorsable() {
		super();
	}


	// Relationships ----------------------------------------------------------

	private Endorsement	endorsement;


	@NotNull
	@Valid
	@OneToMany
	public Endorsement getEndorsement() {
		return this.endorsement;
	}

	public void setEndorsement(final Endorsement endorsement) {
		this.endorsement = endorsement;
	}

}
