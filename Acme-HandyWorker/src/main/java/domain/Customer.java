
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorsable {

	// Constructors

	public Customer() {
		super();
	}


	// Atributes

	private Double	score;


	@Digits(integer = 3, fraction = 2)
	@Range(min = -1, max = 1)
	public Double getScore() {
		return this.score;
	}

	public void setScore(final Double score) {
		this.score = score;
	}


	// Relationships ----------------------------------------------------------

	private Collection<FixUpTask>	fixUpTask;


	@NotNull
	@OneToMany(mappedBy = "Customer")
	public Collection<FixUpTask> getFixUpTask() {
		return this.fixUpTask;
	}

	public void setFixUpTask(final Collection<FixUpTask> fixUpTask) {
		this.fixUpTask = fixUpTask;
	}
}
