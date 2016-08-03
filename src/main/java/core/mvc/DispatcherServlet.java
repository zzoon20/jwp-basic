package core.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;

@WebServlet(name = "dispatcher", urlPatterns = { "", "/" }, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private RequestMapping rm;
	private AnnotationHandlerMapping ahm;

	@Override
	public void init() throws ServletException {
		rm = new RequestMapping();
		rm.initMapping();
		ahm = new AnnotationHandlerMapping("next.controller");
		ahm.initialize();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

		Controller controller = rm.findController(req.getRequestURI());
		ModelAndView mav;
		try {
			if (controller != null) {
				mav = controller.execute(req, resp);
			} else {
				HandlerExecution he = ahm.getHandler(req);
				mav = he.handle(req, resp);
			}

			View view = mav.getView();
			view.render(mav.getModel(), req, resp);
		} catch (Throwable e) {
			logger.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}
}
