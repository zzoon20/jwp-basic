package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	public String excute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
