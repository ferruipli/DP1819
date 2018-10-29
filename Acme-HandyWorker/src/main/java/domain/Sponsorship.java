
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class Sponsorship extends DomainEntity {

	// Constructor

	public Sponsorship() {
		super();
	}


	// Attributes

	private String	banner;
	private String	targetPage;


	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	@NotBlank
	public String getTargetPage() {
		return this.targetPage;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

}
