
package domain;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class EndorserRecord extends DomainEntity {

	// Constructor

	public EndorserRecord() {
		super();
	}


	// Atributes

	private String	fullName;
	private String	email;
	private String	phoneNumber;
	private String	linkedInProfile;
	private String	comments;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@Pattern(regexp = "[A-Za-z_.]+[\\w]+@[a-z-]+\\.[a-z]+")
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}

	@URL
	@NotBlank
	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
	}

	@Pattern(regexp = "^(?!\\s*$).+")
	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
