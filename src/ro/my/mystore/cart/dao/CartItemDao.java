package ro.my.mystore.cart.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.book.domain.Book;
import ro.my.mystore.cart.domain.CartItem;
import ro.my.mystore.user.domain.User;

public class CartItemDao {
	private QueryRunner qr = new TxQueryRunner();
	
	private CartItem toCartItem(Map<String, Object> map) {
		if(map == null || map.size()==0) return null;
		CartItem cartItem = CommonUtils.toBean(map, CartItem.class);
		Book book = CommonUtils.toBean(map, Book.class);
		User user = CommonUtils.toBean(map, User.class);
		cartItem.setBook(book);
		cartItem.setUser(user);
		return cartItem;
	}
	/**
	 * 把多个map映射成多个CartItem
	 * @param mapList
	 * @return
	 */
	private List<CartItem> toCartItemList(List<Map<String,Object>> mapList){
		List<CartItem> cartItemList = new ArrayList<CartItem>();
		for(Map<String,Object> map : mapList) {
			CartItem cartItem = toCartItem(map);
			cartItemList.add(cartItem);
		}
		return cartItemList;
	}
	
	/**
	 * 通过用户查询购物车条目
	 * @throws SQLException 
	 */
	public List<CartItem> findByUser(String uid) throws SQLException{
		String sql = "select * from t_cartitem c,t_book b where c.bid=b.bid and uid=?";
		List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(),uid);
		return toCartItemList(mapList);
	}
	/**
	 * 通过用户id和书籍id找到购物条目
	 * @throws SQLException 
	 */
	public CartItem findByUidAndBid(String uid,String bid) throws SQLException {
		String sql="select * from t_cartitem where uid = ? and bid = ?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),uid,bid);
		CartItem cartItem=toCartItem(map);
		return cartItem;
	}
	/**
	 * 通过条目id和数量完成修改
	 * @throws SQLException 
	 */
	public void updateQuantity(String cartItemId,int quantity) throws SQLException {
		String sql="update t_cartItem set quantity = ? where cartItemId = ? ";
		qr.update(sql,quantity,cartItemId);
	}
	
	public CartItem findByCartItemId(String cartItemId) throws SQLException {
		String sql = "select * from t_cartitem c,t_book b where c.bid = b.bid and c.cartItemId = ?";
		Map<String,Object> map = qr.query(sql, new MapHandler(),cartItemId);
		return toCartItem(map);
		
	}
	/**
	 * 增加一条购物车条目
	 * @throws SQLException 
	 */
	public void addCartItem(CartItem cartItem) throws SQLException {
		String sql = "insert into t_cartItem(cartItemId,quantity,bid,uid)"
				+ "values(?,?,?,?)";
		Object[] params = {cartItem.getCartItemId(),cartItem.getQuantity(),
				cartItem.getBook().getBid(),cartItem.getUser().getUid()};
				qr.update(sql,params);
		}
	
	private String toWhereSql(int len) {
		StringBuilder sb = new StringBuilder("cartItemId in(");
		for(int i=0;i<len;i++) {
			sb.append("?");
			if(i<len-1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	/**
	 * 批量删除
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public void batchDelete(String cartItemIds) throws SQLException {
		/**
		 * 需要先把cartItem转换成数组
		 */
		Object[] cartItemIdArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		String sql = "delete from t_cartitem where "+whereSql;
		qr.update(sql,cartItemIdArray);//其中cartItemArray必须是Object类型的数组
	}
	
	public List<CartItem> loadCartItems(String cartItemIds) throws SQLException{
		Object[] cartItemIdArray = cartItemIds.split(",");
		String whereSql = toWhereSql(cartItemIdArray.length);
		String sql ="select * from t_cartitem c,t_book b where c.bid=b.bid and "+whereSql;
		return toCartItemList(qr.query(sql,new MapListHandler(),cartItemIdArray));
	}
	
}
