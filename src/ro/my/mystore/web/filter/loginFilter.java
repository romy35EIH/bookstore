package ro.my.mystore.web.filter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class loginFilter
 */
@WebFilter({ 
		"/loginFilter",
		"/jsps/cart/*", 
		"/jsps/order/*",
		"/CartItemServlet",
		"/OrderServlet"
})
public class loginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/**
		 * 1. 获取session中的user
		 * 2. 判断是否为null
		 *  >如果为null：保存错误信息到msg
		 *  >如果为true：放行
		 */
		HttpServletRequest req=(HttpServletRequest) request;
		Object user = req.getSession().getAttribute("sessionUser");
		if(user == null) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "请先进行登录");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);;
		}else {
			chain.doFilter(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {  
		
	}

}

