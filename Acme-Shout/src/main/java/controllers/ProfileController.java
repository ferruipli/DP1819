/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import forms.Calculator;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/action-1", method = RequestMethod.GET)
	public ModelAndView action1() {
		ModelAndView result;
		List<String> quotes;

		quotes = new ArrayList<String>();
		quotes.add("Un ordenador es para m� la herramienta m�s sorprendente que hayamos ideado. Es el equivalente a una bicicleta para nuestras mentes. -- Steve Jobs");
		quotes.add("Los ordenadores te ense�an algo importante, y es que no tiene sentido recordarlo todo. Lo importante es ser capaz de encontrar cosas. -- Douglas Coupland ");
		quotes.add("No puedo ense�ar nada a nadie. Solo puedo hacerles pensar -- S�crates.");
		quotes.add("Es durante nuestros momentos m�s oscuros cuando tenemos que centrarnos para ver la luz. -- Arist�teles.");
		quotes.add("La vida es muy simple pero insistimos en hacerla complicada -- Confucio.");
		quotes.add("Nunca morir�a por mis creencias porque podr�a estar equivocado -- Bertrand Russell.");
		quotes.add("No puedo volver al pasado porque entonces era una persona distinta -- Lewis Carroll.");
		quotes.add("Las dificultades preparan a menudo a una persona normal para un destino extraordinario -- C.S. Lewis.");
		quotes.add("La vida es un naufragio, pero no hay que olvidar cantar en los botes salvavidas -- Voltaire.");
		quotes.add("Pienso luego existo (Cogito, ergo sum) -- Descartes.");
		quotes.add("Uno no puede pisar dos veces el mismo r�o -- Her�clito.");
		quotes.add("Solo hay un Dios, el conocimiento, y un demonio, la ignorancia -- S�crates.");
		Collections.shuffle(quotes);
		quotes = quotes.subList(0, 3);

		result = new ModelAndView("profile/action-1");
		result.addObject("quotes", quotes);

		return result;
	}

	// Action-2 GET---------------------------------------------------------------		

	@RequestMapping(value = "/action-2", method = RequestMethod.GET)
	public ModelAndView action2Get() {
		ModelAndView result;
		Calculator calculator;

		calculator = new Calculator();

		result = new ModelAndView("profile/action-2");
		result.addObject("calculator", calculator);

		return result;
	}
	// Action-2 POST ---------------------------------------------------------------		

	@RequestMapping(value = "/action-2", method = RequestMethod.POST)
	public ModelAndView action2Post(@Valid final Calculator calculator, final BindingResult binding) {

		ModelAndView result;

		calculator.compute();

		result = new ModelAndView("profile/action-2");
		result.addObject("calculator", calculator);

		return result;

	}
	// Action-3 ---------------------------------------------------------------		

	@RequestMapping("/action-3")
	public ModelAndView action3() {
		throw new RuntimeException("Oops! An *expected* exception was thrown. This is normal behaviour.");
	}

}
