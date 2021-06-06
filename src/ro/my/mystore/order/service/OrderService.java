package ro.my.mystore.order.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;

import cn.itcast.jdbc.JdbcUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import ro.my.mystore.order.dao.OrderDao;
import ro.my.mystore.order.domain.Order;
import ro.my.mystore.pager.PageBean;
import ro.my.mystore.user.domain.User;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 我的订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> myOrders(String uid,int pc){
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByUser(uid, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询全部订单
	 * @param uid
	 * @param pc
	 * @return
	 */
	public PageBean<Order> findAll(int pc){
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findAll(pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 生成订单
	 * @param order
	 */
	public void CreateOrder(Order order){
		try {
			JdbcUtils.beginTransaction();
			orderDao.add(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据状态查询
	 */
	public PageBean<Order> findByStatus(int status,int pc){
		try {
			JdbcUtils.beginTransaction();
			PageBean<Order> pb = orderDao.findByStatus(status, pc);
			JdbcUtils.commitTransaction();
			return pb;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 */
	public Order load(String oid) {
		try {
			JdbcUtils.beginTransaction();
			Order order = orderDao.load(oid);
			JdbcUtils.commitTransaction();
			return order;
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询订单状态
	 */
	public int findStatus(String oid) {
		try {
			return orderDao.findStatus(oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 修改订单状态
	 */
	public void updateStatus(String oid,int status) {
		try {
			orderDao.updateStatus(oid, status);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public void payment(String oid,User user) {
		/**
		 * 发邮件
		 */
		/**
		 * 把配置文件的内容加载到prop中
		 */
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
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
		String content=MessageFormat.format(prop.getProperty("content"), oid);
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
	public void paymentSucc(String oid) {
		
	}
}