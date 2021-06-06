package ro.my.mystore.admin.admin.service;

import java.sql.SQLException;

import ro.my.mystore.admin.admin.dao.AdminDao;
import ro.my.mystore.admin.admin.domain.Admin;

public class AdminService {
	private AdminDao adminDao = new AdminDao();

	public Admin login(Admin admin) {
		try {
			return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
}
