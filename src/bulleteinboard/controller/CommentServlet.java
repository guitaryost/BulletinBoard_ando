package bulleteinboard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulleteinboard.bean.Comment;
import bulleteinboard.bean.User;
import bulleteinboard.bean.UserComment;
import bulleteinboard.bean.UserMessage;
import bulleteinboard.service.CommentService;
import bulleteinboard.service.MessageService;

@WebServlet(urlPatterns = { "/Comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> errorMessages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Comment comment = new Comment();
		comment.setText(request.getParameter("comment"));
		comment.setUserId(user.getId());
		comment.setMessageId(Integer.parseInt(request.getParameter("messageId")));

		if (isValid(request, errorMessages) == true) {
			new CommentService().register(comment);
			response.sendRedirect("./");

		} else {
			String category = request.getParameter("category");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			if(StringUtils.isEmpty(fromDate)) {
				// fromDateが空・NULLだったら、DBから一番古い日付を取得し、セットする
				String date = new MessageService().getOldDate().getInsertDate().toString();
				fromDate = date;
			}

			if(StringUtils.isEmpty(toDate)) {
				// toDateが空・NULLだったら、今の時刻を取得し、セットする
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				toDate = date;
			}

			List<UserMessage> messages = new MessageService().getMessage(category, fromDate, toDate);
			List<UserComment> comments = new CommentService().getComments();

			request.setAttribute("messages", messages);
			request.setAttribute("comments", comments);
			session.setAttribute("errorMessages", errorMessages);
			request.getRequestDispatcher("top.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> comments) {

		String comment = request.getParameter("comment");

		if (StringUtils.isEmpty(comment)) {
			comments.add("コメントを入力してください");
		}
		if (500 < comment.length()) {
			comments.add("コメントは500文字以下で入力してください");
		}
		if (comments.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
