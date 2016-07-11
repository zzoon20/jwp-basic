package next;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispathcer", urlPatterns = { "", "/" }, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("request = {}", req.getRequestURI());
		
		HttpServlet servlet = RequestMapping.getController(req.getRequestURI());
		servlet.service(req, resp);
	}
}
