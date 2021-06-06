package ro.my.mystore.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import ro.my.mystore.cart.dao.CartItemDao;
import ro.my.mystore.cart.domain.CartItem;

public class CartItemService {
	CartItemDao cartItemDao = new CartItemDao();
	public List<CartItem> myCart(String uid){
		try {
			return cartItemDao.findByUser(uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加条目
	 * @throws SQLException 
	 */
	public void add(CartItem cartItem) {
		/**
		 * 使用UID和bid去查询这个条目是否存在
		 */
		try {
			CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(),cartItem.getBook().getBid());
		if(_cartItem == null ) {
			//如果原来没有这个条目
			cartItem.setCartItemId(CommonUtils.uuid());
			cartItemDao.addCartItem(cartItem);
		}
		else {
			int quantity = cartItem.getQuantity()+_cartItem.getQuantity();
			cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
		}
		} catch (SQLException e) {
			throw new RuntimeException(e);
			}
		
	}
	
	/**
	 * 批量删除
	 */
	public void batchDelete(String cartItemIds) {
		try {
			cartItemDao.batchDelete(cartItemIds);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public CartItem updateQuantity(String cartItemId,int quantity) {
		try {
			cartItemDao.updateQuantity(cartItemId, quantity);
			return cartItemDao.findByCartItemId(cartItemId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载多个cartItem
	 * @param cartItemIds
	 * @return
	 */
	public List<CartItem> loadCartItems(String cartItemIds){
		try {
			return cartItemDao.loadCartItems(cartItemIds);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
