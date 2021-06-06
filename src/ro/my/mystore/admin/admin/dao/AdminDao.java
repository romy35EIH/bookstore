package ro.my.mystore.admin.admin.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.admin.admin.domain.Admin;

public class AdminDao {
	QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 通过管理员登录密码和登录名找到用户
	 * @param adminname
	 * @param adminpwd
	 * @return
	 * @throws SQLException
	 */
	public Admin find(String adminname,String adminpwd) throws SQLException {
		String sql="select * from t_admin where adminname=? and adminpwd = ?";
		return qr.query(sql, new BeanHandler<Admin>(Admin.class),adminname,adminpwd);
	}
	
}
