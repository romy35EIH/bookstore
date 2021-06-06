package ro.my.mystore.user.web.servlet;

import cn.itcast.commons.CommonUtils;

import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.user.domain.User;
import ro.my.mystore.user.service.UserService;
import ro.my.mystore.user.service.exception.UserException;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 用户模块控制层
 */
/**
 * Servlet implementation class UserServlet
 */

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//依赖功能层
	private UserService userService = new UserService();

	/**
	 * 注册功能
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		/**
		 * 1. 封装表单数据
		 */
		User formUser=CommonUtils.toBean(request.getParameterMap(), User.class);
		/**
		 * 2. 校验
		 */
		Map<String,String> errors = validateRegist(formUser,request.getSession());
		if(errors.size()>0) {
			request.setAttribute("form", formUser);
			request.setAttribute("errors",errors);
			return "f:/jsps/user/regist.jsp";
		}
		/**
		 * 3. 调用service完成与数据库的交互
		 */
		userService.regist(formUser);
		/**
		 * 4. 保存成功信息，转发到msg.jsp展示
		 */
		request.setAttribute("code", "success");
		request.setAttribute("msg", "注册成功，请马上到邮箱激活！");
		return "f:/jsps/msg.jsp";
	}
	
	/**
	 * 用户名是否注册
	 * @throws SQLException 
	 */
	public String ajaxValidateLoginname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		// TODO Auto-generated method stub
		/**
		 * 1. 获取参数
		 */
		String loginname = request.getParameter("loginname");
		/**
		 * 2. 通过service得到校验结果
		 */
		
		boolean b = userService.ajaxValidateLoginname(loginname);
		/**
		 * 3. 发回客户端
		 */
		response.getWriter().print(b);
		
		return null;
	}
	
	/**
	 * 邮箱是否正确校验
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 1. 获取参数
		 */
		String email = request.getParameter("email");
		/**
		 * 2. 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/**
		 * 3. 发回客户端
		 */
		response.getWriter().print(b);
		return null;
	}
	/**
	 * 验证码是否正确
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String verifyCode=request.getParameter("verifyCode");
		String vcode =(String) request.getSession().getAttribute("vCode");
		boolean b=verifyCode.equalsIgnoreCase(vcode);
		response.getWriter().print(b);
		return null;
	}
	/**
	 * 激活功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/**
		 * 1. 获取参数激活码
		 * 2. 用激活码调用service方法完成激活
		 * 3. 若抛出异常，把异常信息保存下来，展示在msg页面上
		 * 4. 若没有异常，保存成功信息，展示在msg页面上
		 */
		String code = request.getParameter("activationCode");
		try {
			userService.activation(code);
			request.setAttribute("code", "success");
			request.setAttribute("msg", "激活成功！点击进行登录！");
		} catch (UserException e) {
			//应对异常
			request.setAttribute("msg",e.getMessage());
			request.setAttribute("code","error");
		}
		return "f:/jsps/msg.jsp";
	}
	/**
	 * 校验用户信息是否符合要求
	 * @param formUser
	 * @return
	 */
	private Map<String,String> validateRegist(User formUser,HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/**
		 * 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname== null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空");
		}else if(loginname.length() < 3 || loginname.length()>20) {
			errors.put("loginname", "用户名需在3~20之间!");
		}else if(!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册");
		}
		/**
		 * 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass== null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空");
		}else if(loginpass.length() < 3 || loginpass.length()>20) {
			errors.put("loginpass", "密码长度需在3~20之间!");
		}
		/**
		 * 校验确认密码
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass== null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空");
		}else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致");
		}
		/**
		 * 校验email
		 */
		String email = formUser.getEmail();
		if(email== null || email.trim().isEmpty()) {
			errors.put("email", "email不能为空");
		}else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "email格式错误");
		}else if(!userService.ajaxValidateEmail(loginname)) {
			errors.put("email", "email已被注册");
		}
		/**
		 * 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode=(String)session.getAttribute("vCode");
		if(verifyCode== null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空");
		}else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误");
		}
		return errors;
	}
	
	/**
	 * 登录功能
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		/**
		 * 1. 封装数据到表单中
		 */
		User formUser=CommonUtils.toBean(request.getParameterMap(), User.class);
		/**
		 * 2. 校验表单数据
		 */
		Map<String,String> errors = validateLogin(formUser,request.getSession());
		if(errors.size()>0) {
			request.setAttribute("form", formUser);
			request.setAttribute("errors",errors);
			return "f:/jsps/user/login.jsp";
		}
		/**
		 * 3. 使用service进行查询，得到User
		 */
		User user = userService.login(formUser);
		/**
		 * 4. 查看用户是否存在，如果不存在
		 *    保存错误信息：用户名或密码错误
		 *    保存用户信息
		 *    转发到login.jsp
		 */
		if(user==null) {
			request.setAttribute("msg", "用户名或密码错误！");
			request.setAttribute("user", formUser);
			return "f:/jsps/user/login.jsp";
		}
		else {
			if(user.getStatus()==false){
				request.setAttribute("msg", "您还未激活！");
				request.setAttribute("user", formUser);
				return "f:/jsps/user/login.jsp";
				
			}
			else {
				request.getSession().setAttribute("sessionUser", user);			
				String loginname= user.getLoginname();
				loginname= URLEncoder.encode(loginname,"utf-8");
				Cookie cookie= new Cookie("loginname",loginname);
				cookie.setMaxAge(1000*60*60*24*10);//保存10天
				response.addCookie(cookie);
				return "r:/index.jsp";
				}
		}
		/**
		 * 5. 如果存在，查看状态，如果状态为false：
		 * 	  保存错误信息：没有激活
		 *   保存表单数据
		 *   转发到login.jsp
		 */
		/**
		 * 6. 登陆成功：
		 * 	保存当前查询出的user到session中
		 * 	保存用户的名称到cookie中，注意中文需要编码处理
		 */
	}
	
	/**
	 * 登录功能的校验方法
	 */
	private Map<String,String> validateLogin(User formUser,HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		return errors;
	}
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	public String updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		/**
		 * 封装表单数据到user中
		 */
		User formUser = CommonUtils.toBean(request.getParameterMap(), User.class);
		System.out.println(formUser.getNewpass());
		User user=(User)request.getSession().getAttribute("sessionUser");
		if(user==null) {
			request.setAttribute("msg", "您还没有登录");
			return "f:/jsps/user/login.jsp";
		}
		try {
			userService.updatePassword(user.getUid(), formUser.getNewpass(), formUser.getLoginpass());
			request.setAttribute("msg", "修改密码成功");
			request.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			// TODO Auto-generated catch block
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", formUser);//为了回显
			return "f:/jsps/user/pwd.jsp";
		}
		/**
		 * 从session中获取数据
		 */
		/**
		 * 使用uid和表单中的op和np调用service方法
		 */
	}
	/**
	 * 退出功能
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		request.getSession().invalidate();
		return "r:/jsps/user/login.jsp";
	}
}
