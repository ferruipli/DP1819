
package controllers;

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

import services.CurriculumService;
import services.EducationRecordService;
import services.EndorserRecordService;
import services.HandyWorkerService;
import services.MiscellaneousRecordService;
import services.ProfessionalRecordService;
import utilities.internal.PaginatedListAdapter;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	// Services --------------------------------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;


	// Action-1 ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int handyWorkerId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(this.handyWorkerService.findOne(handyWorkerId).getCurriculum().getId());

		Page<EducationRecord> educationRecords;
		Page<ProfessionalRecord> professionalRecords;
		Page<MiscellaneousRecord> miscellaneousRecords;
		Page<EndorserRecord> endorserRecords;
		Pageable pageable;
		PaginatedList educationRecordsAdapted;
		PaginatedList professionalRecordsAdapted;
		PaginatedList miscellaneousRecordsAdapted;
		PaginatedList endorserRecordsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		educationRecords = this.educationRecordService.findEducationRecordByCurriculum(curriculum, pageable);
		professionalRecords = this.professionalRecordService.findProfessionalRecordByCurriculum(curriculum, pageable);
		miscellaneousRecords = this.miscellaneousRecordService.findMiscellaneousRecordByCurriculum(curriculum, pageable);
		endorserRecords = this.endorserRecordService.findEndorserRecordByCurriculum(curriculum, pageable);

		educationRecordsAdapted = new PaginatedListAdapter(educationRecords, sort);
		professionalRecordsAdapted = new PaginatedListAdapter(professionalRecords, sort);
		miscellaneousRecordsAdapted = new PaginatedListAdapter(miscellaneousRecords, sort);
		endorserRecordsAdapted = new PaginatedListAdapter(endorserRecords, sort);

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("educationRecords", educationRecordsAdapted);
		result.addObject("professionalRecords", professionalRecordsAdapted);
		result.addObject("miscellaneousRecords", miscellaneousRecordsAdapted);
		result.addObject("endorserRecords", endorserRecordsAdapted);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.create();

		result = this.createEditModelAndView(curriculum);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findByPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/curriculum/display.do?handyWorkerId=" + handyWorker.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}

		return result;
	}

	// Arcillary methods --------------------------

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;

	}
}
