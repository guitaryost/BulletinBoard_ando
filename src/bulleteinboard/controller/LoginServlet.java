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

import bulleteinboard.bean.User;
import bulleteinboard.service.LoginService;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)throws IOException, ServletException{

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)throws IOException, ServletException{

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		//LoginServiceを経由
		LoginService loginService = new LoginService();
		User user = loginService.login(loginId, password);
		//Userオブジェクトが取得できればセッションにセット、さらにトップ画面に遷移させる
		HttpSession session = request.getSession();
		if (user != null) {
		//サーバーにデータを保存
			session.setAttribute("loginUser", user);
			response.sendRedirect("./");

		//Userオブジェクトが取得できなければエラーメッセージを表示させる
		} else {
			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました。");
			session.setAttribute("errorMessages", messages);
			session.setAttribute("loginId", loginId);
			response.sendRedirect("login");
		}

	}

}