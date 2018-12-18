
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.MessageService;
import controllers.AbstractController;
import domain.Message;

@Controller
@RequestMapping(value = "/message/administrator")
public class MessageAdministratorController extends AbstractController {

	@Autowired
	private MessageService	messageService;


	// Constructors -----------------------------------------------------------
	public MessageAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/broadcast", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message messageToBroadcast;

		messageToBroadcast = this.messageService.create();

		result = this.broadcastModelAndView(messageToBroadcast);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/broadcast", method = RequestMethod.POST, params = "send")
	public ModelAndView broadcast(@Valid final Message messageToBroadcast, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.broadcastModelAndView(messageToBroadcast);
		else
			try {
				this.messageService.broadcastMessage(messageToBroadcast);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.broadcastModelAndView(messageToBroadcast, "message.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView broadcastModelAndView(final Message messageToBroadcast) {
		ModelAndView result;

		result = this.broadcastModelAndView(messageToBroadcast, null);

		return result;
	}

	protected ModelAndView broadcastModelAndView(final Message messageToBroadcast, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("message/broadcast");
		result.addObject("messageToBroadcast", messageToBroadcast);

		result.addObject("message", messageCode);

		return result;
	}

}
