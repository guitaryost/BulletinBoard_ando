package bulleteinboard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);

		if(StringUtils.isEmpty(fromDate)) {
			// fromDateが空・NULLだったら、DBから一番古い日付を取得し、セットする
			String date = new MessageService().getOldDate().getInsertDate().toString();
			fromDate = date;
		}else{
			fromDate += " 00:00:00 ";

		}


		if(StringUtils.isEmpty(toDate)) {
			// toDateが空・NULLだったら、今の時刻を取得し、セットする
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			toDate = date;
		}else{
			toDate += " 23:59:59 ";
		}

		List<UserMessage> messages = new MessageService().getMessage(category, fromDate, toDate);
		List<UserComment> comments = new CommentService().getComments();
		List<Message> categories = new MessageService().getCategories();

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.setAttribute("categories", categories);
		request.setAttribute("selectedCategory", category);


		request.getRequestDispatcher("/top.jsp").forward(request, response);
	}

}
