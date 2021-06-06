package ro.my.mystore.category.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.category.domain.Category;


/**
 * 分类持久层
 * @author lb321
 *
 */
public class CategoryDao {
	
	private QueryRunner qr = new TxQueryRunner();
	
	private Category toCategory(Map<String,Object> map) {
		Category category = CommonUtils.toBean(map, Category.class);
		String pid = (String) map.get("pid");
		if(pid!= null) {
			/**
			 * 使用一个父分类对象装载pid，再把该父分类设置给category
			 */
			Category parent = new Category();
			parent.setCid(pid);
			category.setParent(parent);
		}
		return category;
	}
	
	private List<Category> toCategoryList(List<Map<String,Object>> mapList){
		List<Category> categoryList = new ArrayList<Category>();
		for(Map<String,Object> map: mapList) {
			Category c = toCategory(map);
			categoryList.add(c);
		}
		return categoryList;
	}
	
	/**
	 * 返回所有分类
	 * @throws SQLException 
	 */
	public List<Category> findAll() throws SQLException{
		/**
		 * 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList= qr.query(sql, new MapListHandler());
		List<Category> parents = toCategoryList(mapList);
		/**
		 * 循环遍历所有的一级分类，为每个一级分类加载二级分类
		 */
		for(Category parent : parents) {
			List<Category> children = findByParent(parent.getCid());
			parent.setChildren(children);
		}
		return parents;
	}
	/**
	 * 通过父分类id找到子分类列表
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findByParent(String pid) throws SQLException{
		String sql = "select * from t_category where pid = ? order by orderBy";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),pid);
		
		return toCategoryList(mapList);
	}
	
	/**
	 * @throws SQLException 
	 * 添加分类
	 */
	public void add(Category category) throws SQLException {
		String sql = "insert into t_category(cid,cname,pid,`desc`) values(?,?,?,?)";
		/**
		 * 分清是一级分类还是二级分类
		 */
		String pid = null;
		if(category.getParent() !=null) {
			pid = category.getParent().getCid();
		}
		Object[] params= {category.getCid(),category.getCname(),pid,category.getDesc()};
		qr.update(sql,params);
	}
	/**
	 * 找到所有一级分类
	 */
	public List<Category> findParents() throws SQLException{
		/**
		 * 查询出所有一级分类
		 */
		String sql = "select * from t_category where pid is null order by orderBy";
		List<Map<String,Object>> mapList= qr.query(sql, new MapListHandler());
		List<Category> parents = toCategoryList(mapList);
		
		return parents;
	}
	/**
	 * 加载分类
	 * @throws SQLException 
	 */
	public Category load(String cid) throws SQLException {
		String sql = "select * from t_category where cid = ?";
		return toCategory(qr.query(sql, new MapHandler(),cid));
	}
	/**
	 * @throws SQLException 
	 * 
	 */
	public void edit(Category category) throws SQLException {
		String sql = "update t_category set cname=? ,pid = ?,`desc` = ? where cid= ?";
		String pid= null;
		if(category.getParent() != null) {
			pid=category.getParent().getCid();
		}
		Object[] params = {category.getCname(),pid,category.getDesc(),category.getCid()};
		
		qr.update(sql, params);
	}
	/**
	 * 删除某个分类
	 * @throws SQLException 
	 */
	public void delete(String cid) throws SQLException {
		String sql = "delete from t_category where cid = ?";
		qr.update(sql,cid);
	}
	/**
	 * 查询某个分类下的二级分类数目
	 * @throws SQLException 
	 */
	public int findChildrenCountByParent(String pid) throws SQLException {
		String sql= "select count(*) from t_category where pid = ?";
		Number number = (Number)qr.query(sql, new ScalarHandler(),pid);
		return number == null ? 0 : number.intValue();
	}
}
