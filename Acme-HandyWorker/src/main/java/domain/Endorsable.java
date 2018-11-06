
package domain;

import java.util.Collection;

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

	private Collection<Endorsement>	endorsementsSents;
	private Collection<Endorsement>	endorsementsReceiveds;


	@NotNull
	@Valid
	@OneToMany
	public Collection<Endorsement> getEndorsementsSents() {
		return this.endorsementsSents;
	}

	public void setEndorsementsSents(final Collection<Endorsement> endorsementsSents) {
		this.endorsementsSents = endorsementsSents;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<Endorsement> getEndorsementsReceiveds() {
		return this.endorsementsReceiveds;
	}

	public void setEndorsementsReceiveds(final Collection<Endorsement> endorsementsReceiveds) {
		this.endorsementsReceiveds = endorsementsReceiveds;
	}

}
