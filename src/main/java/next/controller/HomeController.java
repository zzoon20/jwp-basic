package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

@Controller
public class HomeController extends AbstractController {
    private QuestionDao questionDao;

    @Inject
    public HomeController(QuestionDao questionDao) {
    	this.questionDao = questionDao;
    }
    
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("index.jsp").addObject("questions", questionDao.findAll());
    }
}
