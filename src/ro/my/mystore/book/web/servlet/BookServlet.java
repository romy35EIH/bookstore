package ro.my.mystore.book.web.servlet;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import ro.my.mystore.order.domain.OrderItem;
import ro.my.mystore.pager.PageBean;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/BookServlet")
public class BookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private BookService bookService = new BookService();
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
     * 按目录查
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String cid = request.getParameter("cid");
		PageBean<Book> pb = bookService.findByCategory(cid, pc);
		pb.setUrl(url);
		request.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
		
	}
	/**
	 * 按作者查
	 */
	public String findByAuthor(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String author = req.getParameter("author");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 按出版社查
	 */
	public String findByPress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		 //String press = new String(req.getParameter("press").getBytes("ISO-8859-1"),"UTF-8");
		//String press = URLDecoder.decode(req.getParameter("press"),"utf-8");
		String press = req.getParameter("press");
		//String press=new String(((String)req.getParameter("press")).getBytes("ISO-8859-1"),"utf-8");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 按图查
	 */
	public String findByBname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		String bname = req.getParameter("bname");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	/**
	 * 多组合查询
	 */
	public String findByCombination(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		int pc = getPc(req);
		/*
		 * 2. 得到url：...
		 */
		String url = getUrl(req);
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		Book criteria = CommonUtils.toBean(req.getParameterMap(), Book.class);
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		PageBean<Book> pb = bookService.findByCombination(criteria, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "f:/jsps/book/list.jsp";
	}
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid = req.getParameter("bid");//获取链接的参数bid
		Book book = bookService.load(bid);//通过bid得到book对象
		req.setAttribute("book", book);//保存到req中
		return "f:/jsps/book/desc.jsp";//转发到desc.jsp
	}
	
	/**
	 * 销量排行
	 */
	public String rank(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Book> bookList = bookService.rank();
		req.setAttribute("bookList", bookList);
		
		return "f:/jsps/body.jsp";
	}

}
