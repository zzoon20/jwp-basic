package next.controller.qna;

import java.util.List;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class DeleteQuestionDelegate {
	public boolean deleteQuestion(long questionId){
		QuestionDao qd = new QuestionDao();
		Question question = qd.findById(questionId);
		
		if (isDeletable(question)) {
			qd.delete(questionId);
			return true;
		}
		
		return false;
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
