package ro.my.mystore.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import ro.my.mystore.user.dao.UserDao;
import ro.my.mystore.user.domain.User;
import ro.my.mystore.user.service.exception.UserException;

/**
 * 用户模块功能层
 * @author lb321
 *
 */
public class UserService {
	private UserDao userDao=new UserDao();
	/**
	 * 用户名校验
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 校验邮箱
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		try {
			return userDao.ajaxValidateEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 注册功能
	 * @param user
	 */
	public void regist(User user) {
		/**
		 * 补齐数据
		 */
		user.setUid(CommonUtils.uuid());
		user.setStatus(false);
		user.setActivationCode(CommonUtils.uuid()+CommonUtils.uuid());
		/**
		 * 向数据库插入
		 */
		try {
			userDao.add(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		/**
		 * 发邮件
		 */
		/**
		 * 把配置文件的内容加载到prop中
		 */
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		/**
		 * 登录邮件服务器
		 */
		String host=prop.getProperty("host");//服务器主机名
		String name=prop.getProperty("username");//登录名
		String pass=prop.getProperty("password");//登录密码
		Session session = MailUtils.createSession(host, name, pass);
		/**
		 * 创建mail对象
		 */
		String from = prop.getProperty("from");
		String to=user.getEmail();
		String subject= prop.getProperty("subject");
		String content=MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail = new Mail(from,to,subject,content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 激活功能
	 * @param code
	 * @throws UserException
	 */
	public void activation(String code) throws UserException {
		/**
		 * 1. 通过激活码查询用户
		 * 2. 如果查不到该用户，说明是无效激活码，抛出异常，给出异常信息
		 * 3. 查看用户状态是否为true，如果为true，抛出异常，给出异常（请不要二次激活
		 * 4. 修改用户状态为true；
		 */
		try {
		User user = userDao.findByCode(code);
		if(user==null) 
			throw new UserException("无效的激活码");
		if(user.getStatus())
			throw new UserException("该用户已激活，不要二次激活！");
		userDao.updateStatus(user.getUid(), true);//修改状态
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * 登录功能
	 * @param user
	 * @return
	 */
	public User login(User user) {
		try {
			return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	/**
	 * 修改密码
	 * @param uid
	 * @param newPass
	 * @param oldPass
	 * @throws UserException
	 */
	public void updatePassword(String uid,String newPass,String oldPass) throws UserException {
		try {
			boolean bool = userDao.findByUidAndPassword(uid, oldPass);
			if(!bool) {
				throw new UserException("旧密码错误");
			}
			userDao.updatePassword(uid, newPass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
}
