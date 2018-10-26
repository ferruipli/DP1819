
package domain;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

public class Note extends DomainEntity {

	// Constructor

	public Note() {
		super();
	}


	// Attributes

	private Date				moment;
	private String				commentCreator;
	private Collection<String>	commentsActors;


	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getCommentCreator() {
		return this.commentCreator;
	}

	public void setCommentCreator(final String commentCreator) {
		this.commentCreator = commentCreator;
	}

	public Collection<String> getCommentsActors() {
		return this.commentsActors;
	}

	public void setCommentsActors(final Collection<String> commentsActors) {
		this.commentsActors = commentsActors;
	}

}
