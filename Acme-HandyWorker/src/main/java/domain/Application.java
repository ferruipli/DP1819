
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

@Entity
public class Application extends DomainEntity {

	// Constructors

	public Application() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private Date				registerMoment;
	private String				status;
	private Double				offeredPrice;
	private Collection<String>	comments;


	@Past
	@NotNull
	public Date getRegisterMoment() {
		return this.registerMoment;
	}

	public void setRegisterMoment(final Date registerMoment) {
		this.registerMoment = registerMoment;
	}

	@Pattern(regexp = "^PENDING|ACCEPTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@Min(0)
	@Digits(integer = 6, fraction = 2)
	public Double getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}

}
