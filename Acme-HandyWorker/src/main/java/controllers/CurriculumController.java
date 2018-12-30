
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
import services.HandyWorkerService;
import utilities.internal.PaginatedListAdapter;
import domain.Curriculum;
import domain.EducationRecord;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController {

	// Services --------------------------------------------------

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private EducationRecordService	educationRecordService;


	// Action-1 ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView action1(@RequestParam final int handyWorkerId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Curriculum curriculum;

		curriculum = this.curriculumService.findOne(this.handyWorkerService.findOne(handyWorkerId).getCurriculum().getId());

		Page<EducationRecord> educationRecords;
		Pageable pageable;
		PaginatedList educationRecordsAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		educationRecords = this.educationRecordService.findEducationRecordByCurriculum(curriculum, pageable);
		educationRecordsAdapted = new PaginatedListAdapter(educationRecords, sort);

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("educationRecords", educationRecordsAdapted);

		return result;
	}
}
