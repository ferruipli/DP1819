
package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Past;

public class Endorsement extends DomainEntity {

	// Constructors

	public Endorsement() {
		super();
	}


	// Atributes

	private Date				moment;
	private Collection<String>	endorsement;


	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public Collection<String> getEndorsement() {
		return this.endorsement;
	}

	public void setEndorsement(final Collection<String> endorsement) {
		this.endorsement = endorsement;
	}

}
