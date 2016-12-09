package bulleteinboard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulleteinboard.bean.User;

@WebServlet(urlPatterns = { "/edit"})
public class UserEditServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{

		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");

		if(session.getAttribute("editUser") == null){
	//		User editUser = new UserService().getUser(loginUser.getId());
	//		session.setAttribute("editUser", editUser);

			request.getRequestDispatcher("userEdit.jsp").forward(request,response);
		}
	}


}
