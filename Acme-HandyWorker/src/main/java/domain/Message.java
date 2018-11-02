
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Constructors

	public Message() {
		super();
	}


	// Atributes

	private Date	sendMoment;
	private String	subject;
	private String	body;
	private String	priority;
	private String	tags;
	private boolean	isSpam;


	@Past
	@NotNull
	public Date getSendMoment() {
		return this.sendMoment;
	}

	public void setSendMoment(final Date sendMoment) {
		this.sendMoment = sendMoment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@Pattern(regexp = "^HIGH|NEUTRAL|LOW$")
	@NotBlank
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public boolean getIsSpam() {
		return this.isSpam;
	}

	public void setIsSpam(final boolean isSpam) {
		this.isSpam = isSpam;
	}


	// Relationship----------------------------------------------------
	private Actor				sender;
	private Collection<Actor>	receives;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Actor> getReceives() {
		return this.receives;
	}

	public void setReceives(final Collection<Actor> receives) {
		this.receives = receives;
	}
}
