
package domain;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class Application extends DomainEntity {

	// Constructors

	public Application() {
		super();
	}


	// Attributes ----------------------------------------------------------------------

	private Date	registerMoment;
	private String	status;
	private Money	offeredPrice;
	private String	comments;
	private String	rejectedReason;


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
	@Valid
	public Money getOfferedPrice() {
		return this.offeredPrice;
	}

	public void setOfferedPrice(final Money offeredPrice) {
		this.offeredPrice = offeredPrice;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@NotBlank
	public String getRejectedReason() {
		return this.rejectedReason;
	}

	public void setRejectedReason(final String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

}
