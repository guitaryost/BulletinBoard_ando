package bulleteinboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulleteinboard.bean.UserComment;
import bulleteinboard.bean.UserMessage;
import bulleteinboard.service.CommentService;
import bulleteinboard.service.MessageService;


@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<UserMessage> messages = new MessageService().getMessage();
		List<UserComment> comments = new CommentService().getComment();

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);

		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
