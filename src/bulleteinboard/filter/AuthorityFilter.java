package bulleteinboard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulleteinboard.bean.User;

@WebFilter(urlPatterns = {"/administer", "/signup", "/edit"})
public class AuthorityFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException{
			try{
				//HttpSessionオブジェクトを取得
				HttpSession session = ((HttpServletRequest)request).getSession();
				User user = (User) session.getAttribute("loginUser");

				List<String> errorMessage = new ArrayList<String>();
				errorMessage.add("権限がありません");

				//権限がなければホームに飛ばす
				if(user.getBranchId() != 1 || user.getDepartmentId() != 1){
					session.setAttribute("errorMessage", errorMessage);
					((HttpServletResponse)response).sendRedirect("./");

					return;
				}

				//権限があれば各Servletを実行させる
				chain.doFilter(request, response);
			}catch (ServletException se){
			}catch (IOException e){
			}
		}
	@Override
	public void init(FilterConfig config)throws ServletException{
	}
	@Override
	public void destroy() {
	}
}
