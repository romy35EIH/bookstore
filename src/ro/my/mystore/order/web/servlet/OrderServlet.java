package ro.my.mystore.order.web.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.book.domain.Book;
import ro.my.mystore.book.service.BookService;
import ro.my.mystore.cart.domain.CartItem;
import ro.my.mystore.cart.service.CartItemService;
import ro.my.mystore.order.domain.Order;
import ro.my.mystore.order.domain.OrderItem;
import ro.my.mystore.order.service.OrderService;
import ro.my.mystore.pager.PageBean;
import ro.my.mystore.user.domain.User;
import ro.my.mystore.user.service.exception.UserException;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
	private CartItemService cartItemService = new CartItemService();
	private BookService bookService = new BookService();
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
     * 按用户查，用户可查看我的id
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		User user =(User)request.getSession().getAttribute("sessionUser");
		
		PageBean<Order> pb = orderService.myOrders(user.getUid(), pc);
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "f:/jsps/order/list.jsp";
		
	}
	
	/**
	 * 创建一个订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 获取购物车条目
		 */
		String cartItemIds=request.getParameter("cartItemIds");
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/**
		 * 创建订单
		 */
		Order order = new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(String.format("%tF %<tT", new Date()));//设置下单时间
		order.setStatus(1);
		order.setAddress(request.getParameter("address"));
		User owner = (User)request.getSession().getAttribute("sessionUser");
		order.setOwner(owner);
		
		BigDecimal total = new BigDecimal("0");
		for(CartItem cartItem : cartItemList) {
			total = total.add(new BigDecimal(cartItem.getSubtotal()+""));
		}
		order.setTotal(total.doubleValue());
		
		/**
		 * 创建List<OrderItem>
		 * 一个cartItem对应一个OrderItem
		 */
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(CartItem cartItem : cartItemList) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderItemId(CommonUtils.uuid());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			orderItemList.add(orderItem);
		}
		order.setOrderItemList(orderItemList);
		
		/**
		 * 调用service完成添加
		 */
		orderService.CreateOrder(order);
		
		//删除购物车条目
		cartItemService.batchDelete(cartItemIds);
		request.setAttribute("order", order);
		
		return "f:/jsps/order/ordersucc.jsp";
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
		return "f:/jsps/order/desc.jsp";
		
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
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "您的订单已取消");
		/**
		 * 修改订单条目对应的书的销量
		 * 1. 通过oid找到各订单条目
		 * 2. 通过各订单条目中的bid找到该图书
		 * 3. 修改图书销量
		 */
		Order order = orderService.load(oid);
		for(OrderItem orderItem : order.getOrderItemList()) {
			Book book = bookService.load(orderItem.getBook().getBid());
			book.setSellnum(book.getSellnum()-orderItem.getQuantity());
			bookService.sellBook(book);
		}
		return "f:/jsps/msg.jsp";
	}
	/**
	 * 确认收货
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String confirm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		/**
		 * 校验状态
		 */
		int status = orderService.findStatus(oid);
		if(status !=3 ) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", "无法确认收货");
			return "f:/jsps/msg.jsp";
		}
		orderService.updateStatus(oid, 4);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "您的订单已确认收货");
		return "f:/jsps/msg.jsp";
	}
	/**
	 * 支付准备
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String paymentPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("order", orderService.load(request.getParameter("oid")));
		return "f:/jsps/order/pay.jsp";
	}
	/**
	 * 进行支付
	 */
	public String payment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 1. 获取参数激活码
		 * 2. 用激活码调用service方法完成激活
		 * 3. 若抛出异常，把异常信息保存下来，展示在msg页面上
		 * 4. 若没有异常，保存成功信息，展示在msg页面上
		 */
		String oid = request.getParameter("oid");
		User user =(User)request.getSession().getAttribute("sessionUser");
		orderService.payment(oid,user);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "邮件已发送，请马上到邮箱完成支付");
		return "f:/jsps/msg.jsp";
	}
	public String paymentSucc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oid = request.getParameter("oid");
		
		orderService.updateStatus(oid, 2);
		
		/**
		 * 支付成功 修改书本销量
		 */
		Order order = orderService.load(oid);
		for(OrderItem orderItem : order.getOrderItemList()) {
			Book book = bookService.load(orderItem.getBook().getBid());
			book.setSellnum(book.getSellnum()+orderItem.getQuantity());
			bookService.sellBook(book);
		}
		request.setAttribute("code", "success");
		request.setAttribute("msg", "支付成功！点击返回主页！");
		
		return "f:/jsps/msg.jsp";
	}
}
