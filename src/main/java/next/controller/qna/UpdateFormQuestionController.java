package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

@Controller
public class UpdateFormQuestionController extends AbstractController {
    private QuestionDao questionDao;

    @Inject
    public UpdateFormQuestionController(QuestionDao questionDao) {
    	this.questionDao = questionDao;
    }
    
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId);
		if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}
		return jspView("/qna/update.jsp").addObject("question", question);
	}
}

