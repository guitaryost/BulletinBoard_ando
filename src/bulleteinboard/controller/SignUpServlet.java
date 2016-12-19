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
		String password_confirm = request.getParameter("password_confirm");
		String name = request.getParameter("name");

		User user = new UserService().getUser(loginId);

		if (StringUtils.isEmpty(loginId)) {
			messages.add("ログインIDを入力してください");
		}else if(!loginId.matches("[0-9a-zA-Z]+$")){
			messages.add("ログインIDは半角英数字で登録してください");
		}else if (6 > loginId.length() || loginId.length() > 20) {
			messages.add("ログインIDは6文字以上20文字以下で登録してください");
		}
		if(user != null){
			messages.add("そのIDはすでに存在しています");
		}

		if(StringUtils.isEmpty(password)) {
				messages.add("パスワードを入力してください");
			}else if(6 > password.length() || password.length() > 255) {
				messages.add("パスワードは6文字以上255文字以下で登録してください");
				//半角文字以外だったらという表現の仕方
			}else if(!loginId.matches("^[^ -~｡-ﾟ]+$")) {
				messages.add("パスワードは半角文字で登録してください");
			}

			if(StringUtils.isEmpty(password_confirm)) {
				messages.add("確認用パスワードを入力してください");
			}else if(!password.equals(password_confirm)) {
				messages.add("パスワードと確認用パスワードが異なります");
		}

		if (StringUtils.isEmpty(name)) {
			messages.add("名前を入力してください");
		}else if (name.length() > 10) {
			messages.add("名前は10文字以下で登録してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}

