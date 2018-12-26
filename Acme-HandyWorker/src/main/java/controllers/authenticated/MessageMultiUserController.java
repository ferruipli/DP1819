
package controllers.authenticated;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping(value = "/message/administrator,customer,handyWorker,referee,sponsor")
public class MessageMultiUserController extends AbstractController {

	// Services

	@Autowired
	private MessageService	messageService;

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	// Contructor

	public MessageMultiUserController() {
		super();
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		Message messageToDisplay;
		Box box;
		Actor actor;

		actor = this.actorService.findPrincipal();
		messageToDisplay = this.messageService.findOne(messageId);
		box = this.boxService.searchBoxByMessageAndActor(messageId, actor.getId());

		result = new ModelAndView("message/display");
		result.addObject("messageToDisplay", messageToDisplay);
		result.addObject("boxId", box.getId());

		return result;
	}

	// Send

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message messageToSend;

		messageToSend = this.messageService.create();

		result = this.createEditModelAndView(messageToSend);

		return result;

	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "send")
	public ModelAndView send(@Valid final Message messageToSend, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageToSend);
		else
			try {
				this.messageService.save(messageToSend);
				result = new ModelAndView("box/administrator,customer,handyWorker,referee,sponsor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageToSend, "message.commit.error");
			}

		return result;

	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message messageToDelete, final BindingResult binding) {
		ModelAndView result;

		try {
			this.messageService.delete(messageToDelete);
			result = new ModelAndView("redirect:/box/administrator,customer,handyWorker,referee,sponsor/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(messageToDelete, "message.commit.error");
		}

		return result;

	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Message messageToSend) {
		ModelAndView result;

		result = this.createEditModelAndView(messageToSend, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message messageToSend, final String messageCode) {
		ModelAndView result;
		Collection<Actor> actors;
		Actor principal;

		principal = this.actorService.findPrincipal();
		actors = this.actorService.findAll();
		actors.remove(principal);

		result = new ModelAndView("message/send");
		result.addObject("messageToSend", messageToSend);
		result.addObject("actors", actors);
		result.addObject("message", messageCode);

		return result;

	}

}
