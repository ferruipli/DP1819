
package controllers.customerhandyWorkerreferee;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.ReportService;
import utilities.internal.PaginatedListAdapter;
import controllers.AbstractController;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("note/customer,handyWorker,referee")
public class NoteMultiuserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ReportService	reportService;

	@Autowired
	private NoteService		noteService;


	// Constructors -----------------------------------------------------------

	public NoteMultiuserController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.create();
		result = this.createEditModelAndView(note, reportId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding, final HttpServletRequest request) {
		ModelAndView result;
		Report report;
		Note saved;
		Integer reportId;
		String paramReportId;

		paramReportId = request.getParameter("reportId");
		reportId = paramReportId == null || paramReportId.isEmpty() ? null : Integer.parseInt(paramReportId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(note, reportId);
		else
			try {
				if (note.getId() == 0) {
					report = this.reportService.findOne(reportId);
					saved = this.noteService.save(report, note);
				} else
					saved = this.noteService.save(note);

				result = new ModelAndView("redirect:display.do?noteId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, reportId, "note.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ModelAndView comment(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		result = this.createEditModelAndView(note);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer reportId, @RequestParam(defaultValue = "1", required = false) final int page, @RequestParam(required = false) final String sort, @RequestParam(required = false) final String dir) {
		ModelAndView result;
		Page<Note> notes;
		Pageable pageable;
		PaginatedList notesAdapted;

		pageable = this.newFixedPageable(page, dir, sort);
		notes = this.noteService.findByReportId(reportId, pageable);
		notesAdapted = new PaginatedListAdapter(notes, sort);

		result = new ModelAndView("note/list");
		result.addObject("notes", notesAdapted);
		result.addObject("reportId", reportId);

		return result;
	}

	// Display ----------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);

		result = new ModelAndView("note/display");
		result.addObject("note", note);

		return result;
	}

	// Other business methods -------------------------------------------------

	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public ModelAndView back(@RequestParam final int noteId) {
		ModelAndView result;
		Integer reportId;

		reportId = this.reportService.findIdByNoteId(noteId);
		result = new ModelAndView("redirect:/note/customer,handyWorker,referee/list.do?reportId=" + reportId);

		return result;
	}

	@InitBinder
	public void allowNullStringBinding(final WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final Integer reportId) {
		ModelAndView result;

		result = this.createEditModelAndView(note, reportId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final Integer reportId, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("reportId", reportId);

		result.addObject("messageCode", messageCode);

		return result;
	}

}
