package ro.my.mystore.manager.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.admin.admin.domain.Admin;
import ro.my.mystore.category.domain.Category;
import ro.my.mystore.manager.domain.Manager;
import ro.my.mystore.manager.service.ManagerService;
import ro.my.mystore.user.service.exception.UserException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManagerServlet
 */
@WebServlet("/ManagerServlet")
public class ManagerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private ManagerService managerService = new ManagerService();   
    
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 封装表单数据到admin
		 */
		Manager form = CommonUtils.toBean(request.getParameterMap(), Manager.class);
		Manager manager = managerService.login(form);
		if(manager == null) {
			request.setAttribute("msg", "用户名或密码错误");
			return "/managerjsps/login.jsp";
		}
		request.getSession().setAttribute("manager", manager);
		return findAll(request, response);
	}

	/**
	 * 添加一名管理员
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 添加表单数据到Category中
		 * 调用service
		 * 条用findall，返回显示
		 */
		Admin admin = CommonUtils.toBean(request.getParameterMap(), Admin.class);
		managerService.add(admin);
		return findAll(request,response);
	}

	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("admins",managerService.findAll());
		return "f:/managerjsps/admin/list.jsp";
	}
	
	/**
	 * 删除一名管理员
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 1. 获取cid，判断一级分类下是否有二级分类
		 * ->存在：保存错误信息，转发
		 * ->不存在：直接删除，再返回list.jsp
		 */
		String adminId = request.getParameter("adminId");
			managerService.delete(adminId);
			return findAll(request, response);
		
	}
	
	/**
	 * 修改管理员信息
	 * @throws UserException 
	 */
	public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String adminId = request.getParameter("adminId");
		Admin admin = managerService.load(adminId);
		request.setAttribute("admin", admin);
		return "f:/managerjsps/admin/edit.jsp";
	}
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UserException {
		Admin admin = CommonUtils.toBean(request.getParameterMap(), Admin.class);
		managerService.updatePassword(admin.getAdminId(), admin.getAdminpwd());
		return findAll(request, response);
	}
	
}
