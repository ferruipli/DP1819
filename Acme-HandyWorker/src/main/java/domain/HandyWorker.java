
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Endorsable {

	// Constructor

	private HandyWorker() {
		super();
	}


	// Atributes

	private String	make;
	private Double	score;


	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@Digits(integer = 3, fraction = 2)
	@Range(min = -1, max = 1)
	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

}
