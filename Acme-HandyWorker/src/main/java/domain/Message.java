
package domain;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

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


	@Past
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

}
