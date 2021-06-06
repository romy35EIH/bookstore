package ro.my.mystore.user.domain;


/**
 * 用户实体类
 * @author lb321
 *
 */
/**
 * 属性来自于
 * 1.数据库用户表：需要把user表查出的数据封装到user对象中
 * 2.表单数据需要封装到user对象中
 * @author lb321
 *
 */
public class User {
	private String uid;//主键，用户id
	private String loginname;//登录名
	private String loginpass;//用户密码
	private String email;//邮箱
	private boolean status;//状态，已激活还是未激活
	private String activationCode;//激活码
	
	//注册表单的内容
	private String reloginpass;//确认密码
	private String verifyCode;//验证码
	private String newpass;//新密码
	public String getReloginpass() {
		return reloginpass;
	}
	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getNewpass() {
		return newpass;
	}
	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}
	//我之前为什么要自己敲!
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpass() {
		return loginpass;
	}
	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	
	@Override
	public String toString() {
		return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass=" + loginpass + ", email=" + email
				+ ", status=" + status + ", activationCode=" + activationCode + ", reloginpass=" + reloginpass
				+ ", verifyCode=" + verifyCode + ", newloginpass=" + newpass + "]";
	}
	
	
}
