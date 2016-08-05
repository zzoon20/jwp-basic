package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.CannotDeleteException;
import next.controller.UserSessionUtils;
import next.service.QnaService;

@Controller
public class DeleteQuestionController extends AbstractController {
	private QnaService qnaService;

    @Inject
	public DeleteQuestionController(QnaService qnaService) {
		this.qnaService = qnaService;
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		try {
			qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
			return jspView("redirect:/");
		} catch (CannotDeleteException e) {
			return jspView("show.jsp")
					.addObject("question", qnaService.findById(questionId))
					.addObject("answers", qnaService.findAllByQuestionId(questionId))
					.addObject("errorMessage", e.getMessage());
		}
	}
}
