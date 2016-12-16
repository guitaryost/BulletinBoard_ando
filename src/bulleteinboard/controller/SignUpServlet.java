package bulleteinboard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulleteinboard.bean.Branch;
import bulleteinboard.bean.Department;
import bulleteinboard.bean.User;
import bulleteinboard.service.BranchService;
import bulleteinboard.service.DepartmentService;
import bulleteinboard.service.UserService;

@WebServlet(urlPatterns = { "/signup" })//signupのURLが呼ばれたときに起動させる
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);

		List<Department>departments = new DepartmentService().getDepatments();
		request.setAttribute("departments", departments);

		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		User user = new User();
		user.setLoginId(request.getParameter("login_id"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branch")));
		user.setDepartmentId(Integer.parseInt(request.getParameter("department")));
		user.setAccount(Boolean.parseBoolean(request.getParameter("account")));

		if (isValid(request, messages) == true) {
			new UserService().register(user);
			response.sendRedirect("administer");
		} else {

			List<Branch> branches = new BranchService().getBranches();
			request.setAttribute("branches", branches);

			List<Department>departments = new DepartmentService().getDepatments();
			request.setAttribute("departments", departments);

			request.setAttribute("signupUser",user);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");



		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}
		if (6 > loginId.length() || loginId.length() > 20) {
			messages.add("ログインIDは6文字以上20文字以下で登録してください");
		}
		if(!loginId.matches("[0-9a-zA-Z]+$")){
			messages.add("ログインIDは半角英数字で登録してください");
		}

		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");

		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}
		if (name.length() > 10) {
			messages.add("名前は10文字以下で登録してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

