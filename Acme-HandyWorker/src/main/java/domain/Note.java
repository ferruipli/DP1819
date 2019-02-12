
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Constructor

	public Note() {
		super();
	}


	// Attributes

	private Date	moment;
	private String	commentCustomer;
	private String	commentHandyWorker;
	private String	commentReferee;


	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Pattern(regexp = "^(?!\\s*$).+")
	public String getCommentCustomer() {
		return this.commentCustomer;
	}

	public void setCommentCustomer(final String commentCustomer) {
		this.commentCustomer = commentCustomer;
	}

	@Pattern(regexp = "^(?!\\s*$).+")
	public String getCommentHandyWorker() {
		return this.commentHandyWorker;
	}

	public void setCommentHandyWorker(final String commentHandyWorker) {
		this.commentHandyWorker = commentHandyWorker;
	}

	@Pattern(regexp = "^(?!\\s*$).+")
	public String getCommentReferee() {
		return this.commentReferee;
	}

	public void setCommentReferee(final String commentReferee) {
		this.commentReferee = commentReferee;
	}

}
