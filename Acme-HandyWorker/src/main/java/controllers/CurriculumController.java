
package controllers;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
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


	// Constructor

	public CurriculumController() {
		super();
	}

	// Creating ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int handyWorkerId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Curriculum curriculum;
		HandyWorker handyWorkerLogin;
		Integer handyWorkerLoginId;

		handyWorkerLogin = this.handyWorkerService.findByPrincipal();
		handyWorkerLoginId = handyWorkerLogin.getId();
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
		//		result.addObject("handyWorkerCurriculumId", handyWorkerId);
		//		if (handyWorkerLoginId != null) {
		//			result.addObject("isHandyWorkerLogin", true);
		//			result.addObject("handyWorkerLoginId", handyWorkerLoginId);
		//		} else
		//			result.addObject("isHandyWorkerLogin", false);

		return result;
	}
}
