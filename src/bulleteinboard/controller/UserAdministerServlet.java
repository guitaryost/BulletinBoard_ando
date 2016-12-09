package bulleteinboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulleteinboard.bean.User;
import bulleteinboard.service.UserAdministerService;


@WebServlet(urlPatterns = {"/administer"})
public class UserAdministerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	//doGet
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException ,IOException {

		List<User> users = new UserAdministerService().getUsers();
		request.setAttribute("users", users);

		request.getRequestDispatcher("/userAdminister.jsp").forward(request, response);
	}
}
