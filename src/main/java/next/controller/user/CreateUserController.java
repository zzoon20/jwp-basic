package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.UserDao;
import next.model.User;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	@Override
	public View execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		User user = new User(
				req.getParameter("userId"), 
				req.getParameter("password"), 
				req.getParameter("name"),
				req.getParameter("email"));
		log.debug("User : {}", user);
		
		UserDao userDao = new UserDao();
		userDao.insert(user);
		return new JspView("redirect:/");
	}
}
