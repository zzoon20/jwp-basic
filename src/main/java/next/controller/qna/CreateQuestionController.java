package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

public class CreateQuestionController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(CreateQuestionController.class);
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Question question = new Question(request.getParameter("writer"), request.getParameter("title"),
				request.getParameter("contents"));
		logger.debug("question = {}", question.toString());
		QuestionDao qd = new QuestionDao();
		qd.insert(question);
		return new ModelAndView(new JspView("redirect:/"));
	}

}
