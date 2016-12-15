package bulleteinboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import bulleteinboard.bean.Message;
import bulleteinboard.bean.UserComment;
import bulleteinboard.bean.UserMessage;
import bulleteinboard.service.CommentService;
import bulleteinboard.service.MessageService;


@WebServlet(urlPatterns = { "/index.jsp"})
public class TopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {



		String category = request.getParameter("category");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		if(StringUtils.isEmpty(fromDate)) {
			// fromDateが空・NULLだったら、DBから一番古い日付を取得し、セットする
//			String date = xxxxx
//			fromDate = date;
		}
		if(StringUtils.isEmpty(toDate)) {
			// toDateが空・NULLだったら、今の時刻を取得し、セットする
//			String date = xxxxx
//			toDate = date;
		}



		List<UserMessage> messages = new MessageService().getMessage(category, fromDate, toDate);
		List<UserComment> comments = new CommentService().getComment();
		List<Message> categories = new MessageService().getCategories();

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.setAttribute("categories", categories);


		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
