package ro.my.mystore.book.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mysql.cj.x.protobuf.MysqlxExpr.Expr;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import ro.my.mystore.book.domain.Book;
import ro.my.mystore.category.domain.Category;
import ro.my.mystore.pager.Expression;
import ro.my.mystore.pager.PageBean;
import ro.my.mystore.pager.PageConstant;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 通用的查询方法
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException 
	 */
	public Book findByBid(String bid) throws SQLException {
		String sql = "SELECT * FROM t_book b, t_category c WHERE b.cid=c.cid AND b.bid=?";
		// 一行记录中，包含了很多的book的属性，还有一个cid属性
		Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
		// 把Map中除了cid以外的其他属性映射到Book对象中
		Book book = CommonUtils.toBean(map, Book.class);
		// 把Map中cid属性映射到Category中，即这个Category只有cid
		Category category = CommonUtils.toBean(map, Category.class);
		// 两者建立关系
		book.setCategory(category);
		
		// 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
		if(map.get("pid") != null) {
			Category parent = new Category();
			parent.setCid((String)map.get("pid"));
			category.setParent(parent);
		}
		return book;
	}
	/**
	 * 按分类查询
	 * @param cid
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCategory(String cid,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("cid","=",cid));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 按书名模糊查询
	 * @param bname
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByBname(String bname,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%"+bname+"%"));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 按作者查
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByAuthor(String author,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("author","like","%"+author+"%"));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 按出版社查
	 * @param press
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByPress(String press,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("press","like","%"+press+"%"));
		return findByCriteria(exprList, pc);
	}
	/**
	 * 多条件组合查询
	 * @param exprList
	 * @param pc
	 * @return
	 * @throws SQLException
	 */
	public PageBean<Book> findByCombination(Book criteria,int pc) throws SQLException{
		List<Expression> exprList = new ArrayList<Expression>();
		exprList.add(new Expression("bname","like","%" + criteria.getBname() + "%"));
		exprList.add(new Expression("author","like","%" + criteria.getAuthor() + "%"));
		exprList.add(new Expression("press","like","%" + criteria.getPress() + "%"));
		return findByCriteria(exprList, pc);
	}
	private PageBean<Book> findByCriteria(List<Expression> exprList,int pc ) throws SQLException {
		/**
		 * 1. 得到ps
		 * 2. 得到tr
		 * 3. 得到beanList
		 * 4. 创建PageBean，返回
		 */
		int ps = PageConstant.BOOK_PAGE_SIZE;//每页记录数
		
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
		String sql = "select count(*) from t_book " + whereSql;
		Number number=(Number)qr.query(sql, new ScalarHandler(),params.toArray());
		int tr = number.intValue();//得到总记录
		/**
		 * 得到当前页记录
		 */
		String sql1 = "select * from t_book " + whereSql + " order by orderBy limit ?,?";
		params.add((pc-1)*ps);//记录当前页首行记录的下标
		params.add(ps);//一共查询几行
		List<Book> beanList = qr.query(sql1, new BeanListHandler<Book>(Book.class),params.toArray());
		
		/**
		 * 创建PageBean,设置参数
		 */
		PageBean<Book> pb = new PageBean<Book>();
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
	 * 查询指定分类下图书的个数
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public int findBookCountByCategory(String cid) throws SQLException {
		String sql = "select count(*) from t_book where cid=?";
		Number cnt = (Number)qr.query(sql, new ScalarHandler(), cid);
		return cnt == null ? 0 : cnt.intValue();
	}
	
	/**
	 * 删除图书
	 * @param bid
	 * @throws SQLException
	 */
	public void delete(String bid) throws SQLException {
		String sql = "delete from t_book where bid= ?";
		qr.update(sql,bid);
	}
	/**
	 * 增加图书
	 * @param book
	 * @throws SQLException
	 */
	public void add(Book book) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "insert into t_book(bid,bname,author,price,currPrice," +
				"discount,press,publishtime,edition,pageNum,wordNum,printtime," +
				"booksize,paper,cid,image_w,image_b)" +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), book.getCategory().getCid(),
				book.getImage_w(),book.getImage_b()};
		qr.update(sql, params);
	}
	/**
	 * 修改图书
	 * @param book
	 * @throws SQLException
	 */
	public void edit(Book book) throws SQLException {
		String sql = "update t_book set bname=?,author=?,price=?,currPrice=?," +
				"discount=?,press=?,publishtime=?,edition=?,pageNum=?,wordNum=?," +
				"printtime=?,booksize=?,paper=?,cid=? where bid=?";
		Object[] params = {book.getBname(),book.getAuthor(),
				book.getPrice(),book.getCurrPrice(),book.getDiscount(),
				book.getPress(),book.getPublishtime(),book.getEdition(),
				book.getPageNum(),book.getWordNum(),book.getPrinttime(),
				book.getBooksize(),book.getPaper(), 
				book.getCategory().getCid(),book.getBid()};
		qr.update(sql, params);
	}
	
	public void sellBook(Book book) throws SQLException {
		String sql = "update t_book set sellnum = ? where bid = ?";
		qr.update(sql,book.getSellnum(),book.getBid());
	}
	
	/**
	 * 选出销量排行前十的书
	 * @throws SQLException 
	 */
	public List<Book> rank() throws SQLException{
		List<Book> bookList = new ArrayList<Book>();
		String sql = "select * from t_book order by sellnum DESC";
		bookList = qr.query(sql, new BeanListHandler<Book>(Book.class));
		bookList = bookList.subList(0, 10);
		return bookList;
	}
}

