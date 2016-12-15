package bulleteinboard.filter;

import java.io.IOException;

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

//すべてのURLに一致させる
@WebFilter("/*")
public class LoginFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException{
		try{
			//HttpSessionオブジェクトを取得
			HttpSession session = ((HttpServletRequest)request).getSession();
			User user = (User) session.getAttribute("loginUser");

			// URLに[login]がない場合
			if(((HttpServletRequest)request).getRequestURI().matches("(?!.*login).*")) {
				//ログイン済みでなければログインページに飛ばす
				if(user == null){
					((HttpServletResponse)response).sendRedirect("login");
					return;
				}
			}

			//ログイン済みであれば各Servletを実行させる
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
