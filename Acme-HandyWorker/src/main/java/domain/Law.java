
package domain;

import org.hibernate.validator.constraints.NotBlank;

public class Law extends DomainEntity {

	// Constructor

	public Law() {
		super();
	}


	// Atributes

	private String	title;
	private String	text;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

}
