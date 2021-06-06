package ro.my.mystore.manager.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.admin.admin.domain.Admin;
import ro.my.mystore.manager.domain.Manager;


public class ManagerDao {
	QueryRunner qr = new TxQueryRunner();
	/**
	 * 删除某个产品管理者
	 * @throws SQLException 
	 */
	public void delete(String adminId) throws SQLException {
		String sql = "delete from t_admin where adminId = ?";
		qr.update(sql,adminId);
	}
	/**
	 * 修改某个产品管理者的登录口令
	 */
	public void updatePassword(String adminId,String password) throws SQLException {
		String sql="update t_admin set adminpwd = ? where adminId = ?";
		qr.update(sql,password,adminId);
	}
	/**
	 * 新增某个销售管理者
	 */
	public void add(Admin admin) throws SQLException {
		String sql="insert into t_admin values(?,?,?)";
		Object [] param= {admin.getAdminId(),admin.getAdminname(),admin.getAdminpwd()};
		qr.update(sql, param);
	}
	public Manager find(String managername, String managerpwd) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from t_manager where managername=? and managerpwd = ?";
		return qr.query(sql, new BeanHandler<Manager>(Manager.class),managername,managerpwd);
	}
	
	
	private Admin toAdmin(Map<String,Object> map) {
		Admin admin = CommonUtils.toBean(map, Admin.class);
		
		return admin;
	}
	
	private List<Admin> toAdminList(List<Map<String,Object>> mapList){
		List<Admin> adminList = new ArrayList<Admin>();
		for(Map<String,Object> map: mapList) {
			Admin a = toAdmin(map);
			adminList.add(a);
		}
		return adminList;
	}
	
	/**
	 * 返回所有分类
	 * @throws SQLException 
	 */
	public List<Admin> findAll() throws SQLException{
		/**
		 * 查询出所有一级分类
		 */
		String sql = "select * from t_admin";
		List<Map<String,Object>> mapList= qr.query(sql, new MapListHandler());
		List<Admin> admins = toAdminList(mapList);
		
		return admins;
	}
	public Admin load(String adminId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_admin where adminId = ?";
		return toAdmin(qr.query(sql, new MapHandler(),adminId));
	}
	
}
