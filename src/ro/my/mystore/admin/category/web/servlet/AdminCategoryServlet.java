package ro.my.mystore.admin.category.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.book.service.BookService;
import ro.my.mystore.category.domain.Category;
import ro.my.mystore.category.service.CategoryService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/admin/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   private CategoryService categoryService = new CategoryService();
   private BookService bookService = new BookService();
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("parents",categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	
	/**
	 * 添加一级分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 添加表单数据到Category中
		 * 调用service
		 * 条用findall，返回显示
		 */
		Category parent = CommonUtils.toBean(request.getParameterMap(), Category.class);
		parent.setCid(CommonUtils.uuid());
		categoryService.add(parent);
		return findAll(request,response);
	}
	/**
	 * 添加二级分类第一步
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		List<Category> parents=categoryService.findParents();
		request.setAttribute("pid", pid);
		request.setAttribute("parents", parents);
		return "f:/adminjsps/admin/category/add2.jsp";
	}
	/**
	 * 添加二级分类第二步
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category child = CommonUtils.toBean(request.getParameterMap(), Category.class);
		child.setCid(CommonUtils.uuid());
		//映射pid
		String pid = request.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		
		categoryService.add(child);
		
		return findAll(request, response);
	}
	/**
	 * 修改分类第一步
	 */
	public String editParentPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Category parent = categoryService.load(cid);
		System.out.println(cid);
		request.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}
	/**
	 * 修改一级分类第二步
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category parent = CommonUtils.toBean(request.getParameterMap(), Category.class);
		categoryService.edit(parent);
		return findAll(request, response);
	}
	
	/**
	 * 修改二级分类第一步
	 */
	public String editChildPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Category child = categoryService.load(cid);
		request.setAttribute("child", child);
		
		request.setAttribute("parents", categoryService.findParents());
		return "f:/adminjsps/admin/category/edit2.jsp";
	}
	/**
	 * 修改二级分类第二步
	 */
	public String editChild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Category child = CommonUtils.toBean(request.getParameterMap(), Category.class);
		String pid = request.getParameter("pid");
		
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);
		categoryService.edit(child);
		return findAll(request, response);
	}
	/**
	 * 删除一级分类
	 */
	public String deleteParent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * 1. 获取cid，判断一级分类下是否有二级分类
		 * ->存在：保存错误信息，转发
		 * ->不存在：直接删除，再返回list.jsp
		 */
		String cid = request.getParameter("cid");
		int cnt=categoryService.FindChildrenCountByParent(cid);
		if(cnt>0) {
			//request.setAttribute("code", "error");
			request.setAttribute("msg", "该分类下存在二级分类，不可删除");
			return "f:/adminjsps/msg.jsp";
		}
		else {
			categoryService.delete(cid);
			return findAll(request, response);
		}
		
	}
	/**
	 * 删除2级分类
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1. 获取cid，即2级分类id
		 * 2. 获取该分类下的图书个数
		 * 3. 如果大于零，保存错误信息，转发到msg.jsp
		 * 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = request.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if(cnt > 0) {
			request.setAttribute("msg", "该分类下还存在图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(request, response);
		}
	}
	
	
	
}
