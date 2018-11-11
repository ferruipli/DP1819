
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Endorsable extends Actor {

	// Constructor

	public Endorsable() {
		super();
	}
<<<<<<< HEAD

=======
>>>>>>> Julia-Branch
}
