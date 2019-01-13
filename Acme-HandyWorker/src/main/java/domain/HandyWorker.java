
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Endorsable {

	// Constructor

	public HandyWorker() {
		super();
	}


	// Atributes
	private String	make;


	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}


	// Relationships ----------------------------------------------------------

	private Finder					finder;
	private Curriculum				curriculum;
	private Collection<Application>	applications;


	@NotNull
	@OneToMany(mappedBy = "handyWorker")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@Valid
	@OneToOne(optional = true)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}
}
