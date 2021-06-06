package ro.my.mystore.order.domain;

import java.util.List;

import ro.my.mystore.user.domain.User;

public class Order {
	private String oid;
	private String ordertime;
	private double total;
	private int status;//订单状态：1未付款，2：已付款但未发货，3：已发货未确认收获，4：确认收货交易成功，5：取消订单
	private String address;
	private User owner;
	
	private List<OrderItem> orderItemList;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
