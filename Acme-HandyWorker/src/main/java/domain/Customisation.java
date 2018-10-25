
package domain;

import java.util.Collection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
public class Customisation extends DomainEntity {

	// Constructors

	public Customisation() {
		super();
	}


	// Atributes

	private String				systemName;
	private String				banner;
	private String				welcomeMessageEn;
	private String				welcomeMessageSp;
	private Double				VAT;
	private String				countryCode;
	private Collection<String>	creditCardMakes;
	private Integer				timeCachedFinderResults;
	private String				maxFinderResults;
	private Collection<String>	spamWords;
	private Collection<String>	positiveWords;
	private Collection<String>	negativeWords;


	@NotBlank
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}

	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@NotBlank
	public String getWelcomeMessageSp() {
		return this.welcomeMessageSp;
	}

	public void setWelcomeMessageSp(final String welcomeMessageSp) {
		this.welcomeMessageSp = welcomeMessageSp;
	}

	@Digits(integer = 3, fraction = 2)
	@Range(min = 0, max = 1)
	public Double getVAT() {
		return this.VAT;
	}

	public void setVAT(final Double vAT) {
		this.VAT = vAT;
	}

	@Pattern(regexp = "\\+\\d{2}+")
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@ElementCollection
	public Collection<String> getCreditCardMakes() {
		return this.creditCardMakes;
	}

	public void setCreditCardMakes(final Collection<String> creditCardMakes) {
		this.creditCardMakes = creditCardMakes;
	}

	@Range(min = 1, max = 24)
	public Integer getTimeCachedFinderResults() {
		return this.timeCachedFinderResults;
	}

	public void setTimeCachedFinderResults(final Integer timeCachedFinderResults) {
		this.timeCachedFinderResults = timeCachedFinderResults;
	}

	@Range(min = 0, max = 100)
	public String getMaxFinderResults() {
		return this.maxFinderResults;
	}

	public void setMaxFinderResults(final String maxFinderResults) {
		this.maxFinderResults = maxFinderResults;
	}

	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@ElementCollection
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@ElementCollection
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

}
