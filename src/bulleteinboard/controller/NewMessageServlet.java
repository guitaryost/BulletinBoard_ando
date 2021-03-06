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

import bulleteinboard.bean.Message;
import bulleteinboard.bean.User;
import bulleteinboard.service.MessageService;

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	//doGet
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException ,IOException {

			request.getRequestDispatcher("newMessage.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)throws IOException, ServletException{

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User user = (User)session.getAttribute("loginUser");

		Message message = new Message();
		//値をセットするときも.trimを忘れずに
		message.setTitle(request.getParameter("title").trim());
		message.setUserId(user.getId());
		message.setText(request.getParameter("text").trim());
		message.setCategory(request.getParameter("category").trim());

		if (isValid(request, messages) == true){
			new MessageService().register(message);
			response.sendRedirect("./");
		}else{
			request.setAttribute("newMessage", message);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("newMessage.jsp").forward(request, response);

		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		//.trim()でそれぞれの前後に半角スペースが入力されても除去
		String title = request.getParameter("title").trim();
		String text = request.getParameter("text").trim();
		String category = request.getParameter("category").trim();

		if(StringUtils.isEmpty(title)){
			messages.add("タイトルを入力してください");
		}
		if (50 < title.length()) {
			messages.add("タイトルは50文字以下で入力してください");
		}

		if(StringUtils.isEmpty(text)){
			messages.add("本文を入力してください");
		}
		if (1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}

		if(StringUtils.isEmpty(category)){
			messages.add("カテゴリーを入力してください");
		}

		if (10 < category.length()) {
			messages.add("カテゴリーは10文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
