
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

	private Endorsement	endorsementsSent;
	private Endorsement	endorsementsReceived;


	@NotNull
	@Valid
	@OneToMany
	public Endorsement getEndorsementsReceived() {
		return this.endorsementsReceived;
	}

	public void setEndorsementsReceived(final Endorsement endorsementsReceived) {
		this.endorsementsReceived = endorsementsReceived;
	}

	@NotNull
	@Valid
	@OneToMany
	public Endorsement getEndorsementsSent() {
		return this.endorsementsSent;
	}

	public void setEndorsementsSent(final Endorsement endorsementsSent) {
		this.endorsementsSent = endorsementsSent;
	}

}
