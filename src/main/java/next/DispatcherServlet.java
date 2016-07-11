package next;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.Controller;

@WebServlet(name = "dispathcer", urlPatterns = { "", "/" }, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("request = {}", req.getRequestURI());

		Controller controller = RequestMapping.getController(req.getRequestURI());
		if(controller == null){
			throw new NullPointerException();
		}
		
		try {
			forward(controller.excute(req, resp), req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void forward(String forwardUrl, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if(forwardUrl.startsWith("redirect:")){
			resp.sendRedirect(forwardUrl.replace("redirect:", ""));
			return;
		}
		
		RequestDispatcher rd = req.getRequestDispatcher(forwardUrl);
		rd.forward(req, resp);
	}
}
