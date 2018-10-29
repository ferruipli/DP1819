
package domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class PersonalRecord extends DomainEntity {

	// Constructor

	public PersonalRecord() {
		super();
	}


	// Attributes

	private String	fullName;
	private String	photoLink;
	private String	email;
	private String	phoneNumber;
	private String	linkedProfile;


	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@URL
	@NotBlank
	public String getPhotoLink() {
		return this.photoLink;
	}

	public void setPhotoLink(final String photoLink) {
		this.photoLink = photoLink;
	}

	@Email
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

	@URL
	@NotBlank
	public String getLinkedProfile() {
		return this.linkedProfile;
	}

	public void setLinkedProfile(final String linkedProfile) {
		this.linkedProfile = linkedProfile;
	}

}
