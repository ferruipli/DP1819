
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Constructors

	public Actor() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private String	name;
	private String	middleName;
	private String	surname;
	private String	photoLink;
	private String	email;
	private String	phoneNumber;
	private String	address;
	private boolean	isSuspicious;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Pattern(regexp = "[a-zA-Z]*")
	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
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

	@Pattern(regexp = "[A-Za-z_.]+[\\w]+@[a-z-]+\\.[a-z]+")
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

	@Pattern(regexp = "^(?!\\s*$).+")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}


	//Relationships ----------------------------------------------------
<<<<<<< HEAD
	private Collection<SocialProfile>	socialProfiles;
	private Collection<Box>				boxes;
	private UserAccount					userAccount;


	@NotNull
	@OneToMany
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	@NotNull
	@OneToMany
	public Collection<Box> getBoxes() {
		return this.boxes;
	}
=======
	private UserAccount	userAccount;

>>>>>>> Julia-Branch

	@Valid
	@NotNull
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}
}
