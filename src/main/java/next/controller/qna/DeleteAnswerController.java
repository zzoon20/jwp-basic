package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

@Controller
public class DeleteAnswerController extends AbstractController {
	private AnswerDao answerDao;

    @Inject
	public DeleteAnswerController(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long answerId = Long.parseLong(request.getParameter("answerId"));
        
		ModelAndView mav = jsonView();
		try {
			answerDao.delete(answerId);
			mav.addObject("result", Result.ok());
		} catch (DataAccessException e) {
			mav.addObject("result", Result.fail(e.getMessage()));
		}
		return mav;
	}
}
