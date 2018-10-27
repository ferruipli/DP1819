
package domain;

import java.util.Date;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class EducationRecord extends DomainEntity {

	// Constructor

	public EducationRecord() {
		super();
	}


	// Atributes

	private String	titleDiploma;
	private Date	startDate;
	private Date	endDate;
	private String	institution;
	private String	attachment;
	private String	comments;


	@NotBlank
	public String getTitleDiploma() {
		return this.titleDiploma;
	}

	public void setTitleDiploma(final String titleDiploma) {
		this.titleDiploma = titleDiploma;
	}

	@Past
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
