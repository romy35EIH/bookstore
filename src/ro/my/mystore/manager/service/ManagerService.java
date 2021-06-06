package ro.my.mystore.manager.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.commons.CommonUtils;
import ro.my.mystore.admin.admin.domain.Admin;
import ro.my.mystore.category.domain.Category;
import ro.my.mystore.manager.dao.ManagerDao;
import ro.my.mystore.manager.domain.Manager;
import ro.my.mystore.user.service.exception.UserException;

public class ManagerService {
	ManagerDao managerDao = new ManagerDao();
	
	/**
	 * 增加某个管理员
	 */
	public void add(Admin admin) {
		admin.setAdminId(CommonUtils.uuid());
		try {
			managerDao.add(admin);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除某个管理员
	 */
	public void delete(String adminId) {
		try {
			managerDao.delete(adminId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 修改某个管理员的登录口令
	 */
	public void updatePassword(String adminId,String password) throws UserException {
		try {
			managerDao.updatePassword(adminId, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Manager login(Manager form) {
		// TODO Auto-generated method stub
		try {
			return managerDao.find(form.getManagername(), form.getManagerpwd());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}

	public List<Admin> findAll(){
		try {
			return managerDao.findAll();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Admin load(String adminId) {
		// TODO Auto-generated method stub
		
		try {
			return managerDao.load(adminId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
