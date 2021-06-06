package ro.my.mystore.user.dao;



import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.user.domain.User;

/**
 * 用户模块交互层
 * @author lb321
 *
 */
public class UserDao {
	//操作数据库
		private QueryRunner qr = new TxQueryRunner();
		/**
		 * 校验用户名是否注册
		 * @param loginname
		 * @return
		 * @throws SQLException 
		 */
		public boolean ajaxValidateLoginname(String loginname) throws SQLException {
			
			String sql = "select count(1) from t_user where loginname = ?";
			Object obj=qr.query(sql, new ScalarHandler(),loginname);
			Number number=(Number)obj;
			return number.intValue()==0;
			
		}
		/**
		 * 校验邮箱
		 */
		public boolean ajaxValidateEmail(String email) throws SQLException {
			
			String sql="select count(1) from t_user where email = ?";
			Number number=(Number)qr.query(sql,new ScalarHandler(),email);
			return number.intValue()==0;
		}
		/**
		 * 添加用户
		 * @throws SQLException 
		 */
		public void add(User user) throws SQLException {
			String sql="insert into t_user values(?,?,?,?,?,?)";
			Object [] param= {user.getUid(),user.getLoginname(),user.getLoginpass(),user.getEmail(),
					user.getStatus(),user.getActivationCode()};
			qr.update(sql, param);
		}
		/**
		 * 通过激活码查询用户
		 * @throws SQLException 
		 */
		public User findByCode(String code) throws SQLException {
			String sql="select * from t_user where activationCode = ?";
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		}
		/**
		 * 修改用户状态
		 * @throws SQLException 
		 */
		public void updateStatus(String uid,boolean status) throws SQLException {
			String sql = "update t_user set status = ? where uid = ?";
			qr.update(sql,status,uid);
		}
		/**
		 * 按用户名和密码查询
		 * @param loginname
		 * @param loginpass
		 * @return
		 * @throws SQLException 
		 */
		public User findByLoginnameAndLoginpass(String loginname,String loginpass) throws SQLException {
			String sql ="select * from t_user where loginname= ? and loginpass= ?";
			return qr.query(sql,new BeanHandler<User>(User.class),loginname,loginpass);
		}
		/**
		 * 按uid和password查询
		 * @throws SQLException 
		 */
		public boolean findByUidAndPassword(String uid,String password) throws SQLException {
			String sql = "select count(*) from t_user where uid = ? and loginpass = ?";
			Number number = (Number)qr.query(sql, new ScalarHandler(),uid,password);
			return number.intValue()>0;
		}
		/**
		 * 根据uid重设密码
		 * @throws SQLException 
		 */
		public void updatePassword(String uid,String password) throws SQLException {
			String sql="update t_user set loginpass = ? where uid = ?";
			qr.update(sql,password,uid);
		}
		}


