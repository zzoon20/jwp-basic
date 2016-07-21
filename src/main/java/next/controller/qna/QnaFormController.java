package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;

public class QnaFormController extends AbstractController {

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        if(!UserSessionUtils.isLogined(session)){
        	return jspView("redirect:/");
        }
		return jspView("/qna/form.jsp");
	}

}
