
package controllers.handyworkercustomer;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.EndorsableService;
import services.EndorsementService;
import services.HandyWorkerService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Customer;
import domain.Endorsable;
import domain.Endorsement;
import domain.HandyWorker;

@Controller
@RequestMapping("endorsement/handyWorker,customer")
public class EndorsementHandyWorkerCustomerController extends AbstractController {

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private EndorsableService	endorsableService;


	public EndorsementHandyWorkerCustomerController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.findOne(endorsementId);

		result = new ModelAndView("endorsement/display");
		result.addObject("endorsement", endorsement);

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Endorsement> sentEndorsements, receivedEndorsements;
		Pageable pageable;
		PaginatedList paginatedSentEndorsements, paginatedReceivedEndorsements;

		pageable = this.newFixedPageable(page, dir, sort);

		sentEndorsements = this.endorsementService.findSentEndorsements(pageable);
		receivedEndorsements = this.endorsementService.findReceivedEndorsements(pageable);

		paginatedSentEndorsements = new PaginatedListAdapter(sentEndorsements, sort);
		paginatedReceivedEndorsements = new PaginatedListAdapter(receivedEndorsements, sort);

		result = new ModelAndView("endorsement/list");
		result.addObject("sentEndorsements", paginatedSentEndorsements);
		result.addObject("receivedEndorsements", paginatedReceivedEndorsements);
		result.addObject("requestURI", "endorsement/handyWorker,customer/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.create();

		result = this.createEditModelAndView(endorsement);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView result;
		Endorsement endorsement;

		endorsement = this.endorsementService.findOne(endorsementId);

		result = this.createEditModelAndView(endorsement);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement);
		else
			try {
				this.endorsementService.save(endorsement);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;

		try {
			this.endorsementService.delete(endorsement);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "endorsement.commit.error");
		}

		return result;
	}

	// -----------------------------------------
	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String messageCode) {
		ModelAndView result;
		Endorsable principal;
		List<Endorsable> recipients;

		principal = this.endorsableService.findByPrincipal();

		recipients = new ArrayList<Endorsable>();
		if (principal instanceof Customer)
			recipients.addAll(this.handyWorkerService.findEndorsableHandyWorkers(principal.getId()));
		else if (principal instanceof HandyWorker)
			recipients.addAll(this.customerService.findEndorsableCustomers(principal.getId()));
		else
			result = new ModelAndView("redirect:welcome/index.do");

		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		result.addObject("recipients", recipients);
		result.addObject("message", messageCode);

		return result;
	}

}
