package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;

public class ApiDeleteQuestionController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long questionId = Long.parseLong(request.getParameter("questionId"));
		QuestionDao qd = new QuestionDao();
		Question question = qd.findById(questionId);
		
		if (isDeletable(question)) {
			qd.delete(questionId);
		}
		
		ModelAndView mav = jsonView();
		mav.addObject("result", Result.ok());
		return mav;
	}

	private boolean isDeletable(Question question) {
		if (question.getCountOfComment() != 0) {
			return false;
		}
		return isSameWriter(question);
	}

	private boolean isSameWriter(Question question) {
		String writer = question.getWriter();
		AnswerDao adao = new AnswerDao();
		List<Answer> answerList = adao.findAllByQuestionId(question.getQuestionId());

		for (Answer answer : answerList) {
			if (!writer.equals(answer.getWriter()))
				return false;
		}

		return true;
	}

}
