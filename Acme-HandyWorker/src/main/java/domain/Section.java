
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Section extends DomainEntity {

	// Constructor

	public Section() {
		super();
	}


	// Attributes

	private Integer	number;
	private String	title;
	private String	text;
	private String	pictures;


	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

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

	@URL
	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

}
