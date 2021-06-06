package ro.my.mystore.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import ro.my.mystore.category.domain.Category;
import ro.my.mystore.category.service.CategoryService;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet  {
	private static final long serialVersionUID = 1L;
       
	private CategoryService categoryService = new CategoryService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * 1. 通过service找到所有分类
		 * 2. 保存到request中，转发到left.jsp
		 */
		List<Category> parents= categoryService.findAll();
		request.setAttribute("parents", parents);
		return "f:/jsps/left.jsp";
	}

}
