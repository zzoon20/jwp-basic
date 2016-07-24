package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class DeleteQuestionController extends AbstractController {
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DeleteQuestionDelegate dd = new DeleteQuestionDelegate();
		dd.deleteQuestion(Long.parseLong(request.getParameter("questionId")));
		return jspView("redirect:/");
	}
}
