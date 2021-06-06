package ro.my.mystore.cart.web.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.book.domain.Book;
import ro.my.mystore.cart.domain.CartItem;
import ro.my.mystore.cart.service.CartItemService;
import ro.my.mystore.user.domain.User;

/**
 * Servlet implementation class CartItemServlet
 */
@WebServlet("/CartItemServlet")
public class CartItemServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private CartItemService cartItemService = new CartItemService();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public String myCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 得到uid
		 */
		User user = (User)request.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		/**
		 * 通过service得到当前用户的所有购物车条目
		 */
		List<CartItem> cartItemList=cartItemService.myCart(uid);
		/**
		 * 保存起来并转发
		 */
		request.setAttribute("cartItemList", cartItemList);
		return "f:/jsps/cart/list.jsp";
	}
	/**
	 * 添加购物条目
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 封装表单数据
		 */
		CartItem cartItem = CommonUtils.toBean(request.getParameterMap(), CartItem.class);
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		User user = (User)request.getSession().getAttribute("sessionUser");
		cartItem.setBook(book);
		cartItem.setUser(user);
		/**
		 * 调用service
		 */
		cartItemService.add(cartItem);
		/**
		 * 查询出所有条目
		 */
		return myCart(request, response);
	}
	public String batchDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 获取参数数组
		 * 调用sevice
		 * 返回到list.jsp
		 */
		String cartItemIds = request.getParameter("cartItemIds");
		cartItemService.batchDelete(cartItemIds);
		return myCart(request, response);
	}
	/**
	 * 修改条目数量
	 */
	public String updateQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cartItemId = request.getParameter("cartItemId");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);
		/**
		 * 给客户端返回一个json对象
		 */
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");
		
		response.getWriter().print(sb);
		return null;
	}
	public String loadCartItems(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cartItemIds = request.getParameter("cartItemIds");
		double total = Double.parseDouble(request.getParameter("total"));
		//通过service得到该条目
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/**
		 * 给客户端返回一个json对象
		 */
		request.setAttribute("cartItemList", cartItemList);
		request.setAttribute("total", total);
		request.setAttribute("cartItemIds", cartItemIds);
		return "f:/jsps/cart/showitem.jsp";
	}
}
