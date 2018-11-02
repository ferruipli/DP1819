
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
public class Referee extends Actor {

	// Constructor

	public Referee() {
		super();
	}


	// Relationships ----------------------------------------------------------
	private Collection<Complaint>	complaints;


	@NotNull
	@Valid
	@OneToMany
	public Collection<Complaint> getComplaint() {
		return this.complaints;
	}

	public void setComplaint(final Collection<Complaint> complaint) {
		this.complaints = complaint;
	}
}
