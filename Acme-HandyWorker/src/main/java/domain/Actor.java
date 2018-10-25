
package domain;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
public class Actor extends DomainEntity {

	// Constructors

	public Actor() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	name;
	private String	middelName;
	private String	surname;
	private String	photoLink;
	private String	email;
	private String	phoneNumber;
	private String	address;
	private Boolean	isSuspicious;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddelName() {
		return this.middelName;
	}

	public void setMiddelName(final String middelName) {
		this.middelName = middelName;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@URL
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

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final Boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

}
