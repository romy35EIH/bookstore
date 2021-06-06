package ro.my.mystore.category.service;

import java.sql.SQLException;
import java.util.List;



import ro.my.mystore.category.dao.CategoryDao;
import ro.my.mystore.category.domain.Category;

/**
 * 分类模块业务层
 * @author lb321
 *
 */

/**
 * 查询所有分类
 * @author lb321
 *
 */
public class CategoryService {
	CategoryDao categoryDao = new CategoryDao();
	public List<Category> findAll(){
		try {
			return categoryDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void add(Category category) {
		try {
			categoryDao.add(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public List<Category> findParents(){
		try {
			return categoryDao.findParents();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 编辑分类
	 * 
	 * @param category
	 */
	public void edit(Category category) {
		try {
			categoryDao.edit(category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载分类
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		try {
			return categoryDao.load(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int FindChildrenCountByParent(String pid) {
		try {
			return categoryDao.findChildrenCountByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void delete(String cid) {
		try {
			categoryDao.delete(cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Category> findChildren(String pid) {
		try {
			return categoryDao.findByParent(pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
