
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
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


	// Relationships --------------------------------------
	private Endorsable	reciever;
	private Endorsable	senter;


	@Valid
	public Endorsable getSenter() {
		return this.senter;
	}

	public void setSenter(final Endorsable senter) {
		this.senter = senter;
	}

	@Valid
	public Endorsable getReciever() {
		return this.reciever;
	}

	public void setReciever(final Endorsable reciever) {
		this.reciever = reciever;
	}

}
