package ro.my.mystore.admin.admin.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.admin.admin.domain.Admin;
import ro.my.mystore.admin.admin.service.AdminService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private AdminService adminService = new AdminService();
    
    /**
     * 销售人员的也可以这么写
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 封装表单数据到admin
		 */
		Admin form = CommonUtils.toBean(request.getParameterMap(), Admin.class);
		Admin admin = adminService.login(form);
		if(admin == null) {
			request.setAttribute("msg", "用户名或密码错误");
			return "/adminjsps/login.jsp";
		}
		request.getSession().setAttribute("admin", admin);
		return "r:/adminjsps/admin/index.jsp";
		
	}

	

}
