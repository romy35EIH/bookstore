package ro.my.mystore.order.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.book.domain.Book;
import ro.my.mystore.order.domain.Order;
import ro.my.mystore.order.domain.OrderItem;
import ro.my.mystore.pager.Expression;
import ro.my.mystore.pager.PageBean;
import ro.my.mystore.pager.PageConstant;

public class OrderDao {

	QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 生成订单
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public void add(Order order) throws SQLException{
		/**
		 * 插入订单
		 */
		String sql = "insert into t_order values(?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getStatus(),
				order.getAddress(),order.getOwner().getUid()};
		qr.update(sql,params);
		/**
		 * 循环遍历订单的所有条目，每个条目生成一个Object[]数组
		 * 多个条目对应二维数组
		 * 执行批处理，完成插入订单条目
		 */
		sql = "insert into t_orderitem values(?,?,?,?,?,?,?,?)";
		int len = order.getOrderItemList().size();
		Object[][] objs = new Object[len][];
		for(int i = 0;i<len;i++) {
			OrderItem item = order.getOrderItemList().get(i);
			objs[i]=new Object[]{item.getOrderItemId(),item.getQuantity(),item.getSubtotal(),
					item.getBook().getBid(),item.getBook().getBname(),item.getBook().getCurrPrice(),
					item.getBook().getImage_b(),order.getOid()};
		}
		qr.batch(sql, objs);
	}
	
	private PageBean<Order> findByCriteria(List<Expression> exprList,int pc ) throws SQLException {
		/**
		 * 1. 得到ps
		 * 2. 得到tr
		 * 3. 得到beanList
		 * 4. 创建PageBean，返回
		 */
		int ps = PageConstant.ORDER_PAGE_SIZE;//每页记录数
		
		/**
		 * 用exprList来生成where子句
		 */
		StringBuilder whereSql = new StringBuilder("where 1=1");
		List<Object> params= new ArrayList<Object>();//对应问号的值
		for(Expression expr : exprList) {
			whereSql.append(" and ").append(expr.getName()).append(" ").append(expr.getOperator()).append(" ");
			//where 1=1 and bid =
			if(!expr.getOperator().equals("is null")) {
				whereSql.append("?");
				params.add(expr.getValue());
			}
		}
		String sql = "select count(*) from t_order " + whereSql;
		Number number=(Number)qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();//得到总记录
		/**
		 * 得到当前页记录
		 */
		String sql1 = "select * from t_order " + whereSql + " order by ordertime desc limit ?,?";
		params.add((pc-1)*ps);//记录当前页首行记录的下标
		params.add(ps);//一共查询几行
		List<Order> beanList = qr.query(sql1, new BeanListHandler<Order>(Order.class),params.toArray());
		//订单中还没有条目
		//遍历所有订单，得到订单条目
		for(Order order : beanList) {
			loadOrderItem(order);
			
		}
		
		/**
		 * 创建PageBean,设置参数
		 */
		PageBean<Order> pb = new PageBean<Order>();
		pb.setBeanList(beanList);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);
		/**
		 * url由servlet完成
		 */
		
		
		return pb;
		
	}
	
	/**
	 * 为指定的order加载条目
	 * @param order
	 * @throws SQLException 
	 */
	private void loadOrderItem(Order order) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select * from t_orderItem where oid = ?";
		List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),order.getOid());
		List<OrderItem> orderItemList = toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
	}
	/**
	 * 把多个map转换成多个orderItem
	 * @param mapList
	 * @return
	 */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
		// TODO Auto-generated method stub
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		for(Map<String ,Object> map : mapList) {
			OrderItem orderItem = toOrderItem(map);
			orderItemList.add(orderItem);
		}
		return orderItemList;
	}
	
	/**
	 * 把一个map转换成一个orderItem
	 * @param map
	 * @return
	 */

	private OrderItem toOrderItem(Map<String, Object> map) {
		// TODO Auto-generated method stub
		OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	/**
	 * 根据用户查询
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findByUser(String uid,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("uid","=",uid));
		return findByCriteria(exprList, pc);
	}
	
	/**
	 * 查询全部订单
	 * @param uid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Order> findAll(int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		return findByCriteria(exprList, pc);
	}
	
	/**
	 * 加载订单
	 * @param oid
	 * @return
	 * @throws SQLException
	 */
	public Order load(String oid) throws SQLException {
		String sql = "select * from t_order where oid = ?";
		Order order=qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		loadOrderItem(order);//为当前订单加载所有条目
		return order;
	}
	
	/**
	 * 查询订单状态
	 * @throws SQLException 
	 */
	public int findStatus(String oid) throws SQLException {
		String sql = "select status from t_order where oid = ?";
		Number number=(Number)qr.query(sql, new ScalarHandler(),oid);
		return number.intValue();
	}
	/**
	 * 修改订单状态
	 * @throws SQLException 
	 */
	public void updateStatus(String oid,int status) throws SQLException {
		String sql = "update t_order set status = ? where oid = ?";
		qr.update(sql,status,oid);
	}
	
	/**
	 * 按状态查询
	 */
	public PageBean<Order> findByStatus(int status,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("status","=",status+""));
		return findByCriteria(exprList, pc);
	}
}
