package bulleteinboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulleteinboard.bean.Branch;
import bulleteinboard.bean.Department;
import bulleteinboard.bean.User;
import bulleteinboard.service.BranchService;
import bulleteinboard.service.DepartmentService;
import bulleteinboard.service.UserAdministerService;
import bulleteinboard.service.UserService;


@WebServlet(urlPatterns = {"/administer"})
public class UserAdministerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	//doGet
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<User> users = new UserAdministerService().getUsers();
		List<Branch> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);
		List<Department>departments = new DepartmentService().getDepatments();
		request.setAttribute("departments", departments);

		request.setAttribute("users", users);

		request.getRequestDispatcher("/userAdminister.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {

		User user = new User();
		user.setAccount(Boolean.parseBoolean(request.getParameter("account")));
		user.setId(Integer.parseInt(request.getParameter("id")));
		new UserService().stop(user);
		response.sendRedirect("administer");
	}
}
