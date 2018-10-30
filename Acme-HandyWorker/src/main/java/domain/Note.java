
package domain;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

public class Note extends DomainEntity {

	// Constructor

	public Note() {
		super();
	}


	// Attributes

	private Date	moment;
	private String	role;
	private String	commentCustomer;
	private String	commentHandyWorker;
	private String	commentReferee;


	@Past
	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Pattern(regexp = "^CUSTOMER|HANDYWORKER|REFEREE$")
	public String getRole() {
		return this.role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public String getCommentCustomer() {
		return this.commentCustomer;
	}

	public void setCommentCustomer(final String commentCustomer) {
		this.commentCustomer = commentCustomer;
	}

	public String getCommentHandyWorker() {
		return this.commentHandyWorker;
	}

	public void setCommentHandyWorker(final String commentHandyWorker) {
		this.commentHandyWorker = commentHandyWorker;
	}

	public String getCommentReferee() {
		return this.commentReferee;
	}

	public void setCommentReferee(final String commentReferee) {
		this.commentReferee = commentReferee;
	}

}
