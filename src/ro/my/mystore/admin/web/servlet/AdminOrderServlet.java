package ro.my.mystore.admin.web.servlet;

import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.order.domain.Order;
import ro.my.mystore.order.service.OrderService;
import ro.my.mystore.pager.PageBean;
import ro.my.mystore.user.domain.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminOrderServlet
 */
@WebServlet("/admin/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private OrderService orderService = new OrderService();
    
    /**
     * 获取当前页的页码
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
    	int pc = 1;
    	String param = req.getParameter("pc");
    	if(param != null && !param.trim().isEmpty()) {
    		try {
				pc = Integer.parseInt(param);
				
			} catch (RuntimeException e) {
				// TODO: handle exception
			}
    	}
    	return pc;
    }
    
    private String getUrl(HttpServletRequest req) {
    	String url = req.getRequestURI() + "?" + req.getQueryString();
    	/**
    	 * 如果url中存在pc参数，需要剪裁掉
    	 */
    	int index = url.lastIndexOf("&pc");
    	if(index != -1) {
    		url = url.substring(0,index);
    	}
    	return url;
    	
    }
    /**
     * 查询全部订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generate d method stub
		/**
		 * 1. 得到pc;如果页面传递，使用页面的，如果没传，pc=1
		 * 2. 得到url
		 * 3. 获取查询条件
		 * 4. 调用service
		 * 5. 给PageBean设置url，保存pagebean
		 */
		int pc = getPc(request);
		String url = getUrl(request);
		/**
		 * 从当前session中获取
		 */
		
		PageBean<Order> pb = orderService.findAll(pc);
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/list.jsp";
		
	}

    /**
     * 根据状态查询订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findByStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generate d method stub
		/**
		 * 1. 得到pc;如果页面传递，使用页面的，如果没传，pc=1
		 * 2. 得到url
		 * 3. 获取查询条件
		 * 4. 调用service
		 * 5. 给PageBean设置url，保存pagebean
		 */
		int pc = getPc(request);
		String url = getUrl(request);
		/**
		 * 从当前session中获取
		 */
		int status = Integer.parseInt(request.getParameter("status"));
		PageBean<Order> pb = orderService.findByStatus(status, pc);
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "f:/adminjsps/admin/order/list.jsp";
		
	}
    
    /**
	 * 加载订单详细信息
	 */
	public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		Order order = orderService.load(oid);
		request.setAttribute("order", order);
		String btn = request.getParameter("btn");//指出用户意图
		request.setAttribute("btn", btn);
		return "f:/adminjsps/admin/order/desc.jsp";
		
	}
	
	/**
	 * 取消订单
	 */
	public String cancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		/**
		 * 校验状态
		 */
		int status = orderService.findStatus(oid);
		if(status !=1 ) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "订单已在处理，无法取消");
			return "f:/adminjsps/admin/msg.jsp";
		}
		orderService.updateStatus(oid, 5);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "您的订单已取消");
		return "f:/adminjsps/msg.jsp";
	}
	
	/**
	 * 发货
	 */
	public String deliver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		/**
		 * 校验状态
		 */
		int status = orderService.findStatus(oid);
		if(status !=2 ) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "订单还未付款，无法发货");
			return "f:/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 3);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "您的订单已发货");
		return "f:/adminjsps/msg.jsp";
	}
}
