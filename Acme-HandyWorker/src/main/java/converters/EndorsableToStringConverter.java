/*
 * EndorsableToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Endorsable;

@Component
@Transactional
public class EndorsableToStringConverter implements Converter<Endorsable, String> {

	@Override
	public String convert(final Endorsable endorsable) {
		String result;

		if (endorsable == null)
			result = null;
		else
			result = String.valueOf(endorsable.getId());

		return result;
	}

}
