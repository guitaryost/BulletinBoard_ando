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

		//URLのパラメータがなかった場合の処理
		if(StringUtils.isEmpty(id)){
			response.sendRedirect("administer");
		//URLのパラメータに数値以外の処理が入ったときの処理
		}else if(!id.matches("[0-9]+$")){
			response.sendRedirect("administer");
		}else{
			//ユーザーの情報を取得
			User editUser = new UserService().getUser(Integer.parseInt(id));
			//editUserがnull＝存在しないユーザーの情報
			if(editUser != null) {
			session.setAttribute("editUser", editUser);

				List<Branch> branches = new BranchService().getBranches();
				request.setAttribute("branches", branches);
				List<Department>departments = new DepartmentService().getDepatments();
				request.setAttribute("departments", departments);

				request.getRequestDispatcher("userEdit.jsp").forward(request,response);
			}else{
				response.sendRedirect("administer");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		if (isValid(request, messages) == true) {
			User editUser = getEditUser(request);
			try{
				new UserService().update(editUser);

				// 更新したユーザーがログインユーザーと同じなら、セッションに格納されている情報を更新する
				User loginUser = (User)session.getAttribute("loginUser");
				if(loginUser.getId() == editUser.getId()) {
					// データベースから最新の情報を取得する
					User updatedLoginUser = new UserService().getUser(loginUser.getId());
					// セッションに格納する
					session.setAttribute("loginUser", updatedLoginUser);
				}

			}catch(NoRowsUpdatedRuntimeException e){
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認して下さい。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("edit");
			}

			response.sendRedirect("administer");
			return;

		}else{

			List<Branch> branches = new BranchService().getBranches();
			request.setAttribute("branches", branches);
			List<Department>departments = new DepartmentService().getDepatments();
			request.setAttribute("departments", departments);

			User editUser = getEditUser(request);
			request.setAttribute("editUser", editUser);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("userEdit.jsp").forward(request, response);
		}
	}


	private User getEditUser(HttpServletRequest request)throws IOException, ServletException{

		HttpSession session = request.getSession();
		User editUser = (User)session.getAttribute("editUser");

		editUser.setName(request.getParameter("name").trim());
		editUser.setLoginId(request.getParameter("loginId").trim());
		editUser.setPassword(request.getParameter("password").trim());
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch")));
		editUser.setDepartmentId(Integer.parseInt(request.getParameter("department")));
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String loginId = request.getParameter("loginId").trim();
		String name = request.getParameter("name").trim();

		String password = request.getParameter("password").trim();
		String password_confirm = request.getParameter("password_confirm").trim();

		User user = new UserService().getUser(loginId);

		HttpSession session = request.getSession();
		User editUser = (User)session.getAttribute("editUser");

		if(StringUtils.isEmpty(loginId)) {
			messages.add("ログインIDを入力してください");
		}else if(!loginId.matches("[0-9a-zA-Z]+$")) {
			messages.add("ログインIDは半角英数字で登録してください");
		}else if (6 > loginId.length() || loginId.length() > 20) {
			messages.add("ログインIDは6文字以上20文字以下で登録してください");
		}
		if(user != null && user.getId() != editUser.getId()) {
			messages.add("そのログインIDはすでに存在しています");
		}

		if(StringUtils.isEmpty(name)) {
			messages.add("氏名を入力してください");
		}else if (name.length() > 10) {
			messages.add("名前は10文字以下で登録してください");
		}

		if(!StringUtils.isEmpty(password) || !StringUtils.isEmpty(password_confirm)) {
			if(StringUtils.isEmpty(password)) {
				messages.add("パスワードを入力してください");
			}else if(6 > password.length() || password.length() > 255) {
				messages.add("パスワードは6文字以上255文字以下で登録してください");
			}else if(password.matches("^[^ -~｡-ﾟ]+$")) {
				messages.add("パスワードは半角文字で登録してください");
			}

			if(StringUtils.isEmpty(password_confirm)) {
				messages.add("確認用パスワードを入力してください");
			}else if(!password.equals(password_confirm)) {
				messages.add("パスワードと確認用パスワードが異なります");
			}
		}


		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
