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
import bulleteinboard.exception.NoRowsUpdatedRuntimeException;
import bulleteinboard.service.BranchService;
import bulleteinboard.service.DepartmentService;
import bulleteinboard.service.UserService;

@WebServlet(urlPatterns = { "/edit"})
public class UserEditServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		if (session.getAttribute("editUser") == null) {
		User editUser = new UserService().getUser(Integer.parseInt(id));
		session.setAttribute("editUser", editUser);
		}

		List<Branch> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);
		List<Department>departments = new DepartmentService().getDepatments();
		request.setAttribute("departments", departments);

		request.getRequestDispatcher("userEdit.jsp").forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();

		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		if (isValid(request, messages) == true) {
			try{
				new UserService().update(editUser);
			}catch(NoRowsUpdatedRuntimeException e){
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認して下さい。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("edit");
			}

			session.setAttribute("loginUser", editUser);
			session.removeAttribute("editUser");

			response.sendRedirect("administer");
			return;
		}else{
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("edit");
		}
	}


	private User getEditUser(HttpServletRequest request)throws IOException, ServletException{

		HttpSession session = request.getSession();
		User editUser = (User)session.getAttribute("editUser");

		editUser.setName(request.getParameter("name"));
		editUser.setLoginId(request.getParameter("loginId"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch")));
		editUser.setDepartmentId(Integer.parseInt(request.getParameter("department")));
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String name = request.getParameter("name");
		String loginId = request.getParameter("loginId");

		if(StringUtils.isEmpty(name) == true){
			messages.add("氏名を入力してください");
		}
		if(StringUtils.isEmpty(loginId) == true){
			messages.add("ログインIDを入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
