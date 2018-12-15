/*
 * NoteToStringConverter.java
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

import domain.Note;

@Component
@Transactional
public class NoteToStringConverter implements Converter<Note, String> {

	@Override
	public String convert(final Note note) {
		String result;

		if (note == null)
			result = null;
		else
			result = String.valueOf(note.getId());

		return result;
	}

}
