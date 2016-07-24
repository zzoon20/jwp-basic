package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ApiDeleteQuestionController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DeleteQuestionDelegate dd = new DeleteQuestionDelegate();
		boolean result = dd.deleteQuestion(Long.parseLong(request.getParameter("questionId")));
		
		ModelAndView mav = jsonView();
		mav.addObject("result", result);
		return mav;
	}
}
