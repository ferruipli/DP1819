
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	// Constructors

	public Endorsement() {
		super();
	}


	// Atributes

	private Date	moment;
	private String	comments;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}


	// Relationships ----------------------------------------------------------

	private Endorsable	sender;
	private Endorsable	recipient;


	@NotNull
	@ManyToOne(optional = false)
	public Endorsable getSender() {
		return this.sender;
	}

	public void setSender(final Endorsable sender) {
		this.sender = sender;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Endorsable getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Endorsable recipient) {
		this.recipient = recipient;
	}

}
