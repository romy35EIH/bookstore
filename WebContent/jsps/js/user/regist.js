$(function(){
	/**
	 * 1. 找到所有的错误信息，循环遍历之，调用一个方法来确定是否显示错误信息！
	 */
	$(".errorClass").each(function(){
		showError($(this));
	})
	/**
	 * 2. 切换注册按钮的图片
	 * @param ele
	 * @returns
	 */
	$("#submitBtn").hover(
	function(){
		$("#submitBtn").attr("src","/mystore/images/regist2.jpg")
	},
	function(){
		$("#submitBtn").attr("src","/mystore/images/regist1.jpg")
	}
	);
	/**
	 * 3. 输入块得到焦点隐藏错误信息
	 * @param ele
	 * @returns
	 */
	$(".inputClass").focus(function(){
		var labelId=$(this).attr("id")+"Error";
		$("#"+labelId).text("");//把label内容清空
		showError($("#"+labelId));
	}
	);
	/**
	 * 4. 输入框失去焦点进行校验
	 * @param ele
	 * @returns
	 */
	$(".inputClass").blur(function(){
		var id =$(this).attr("id");
		//得到函数名
		var mname = "validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
		eval(mname);
	});
	/**
	 * 表单提交时进行校验
	 */
	$("#registForm").submit(function(){
		var bool = true;
		if(!validateLoginname()){
			bool=false;
		}
		if(!validateLoginpass()){
			bool=false;
		}
		if(!validateReloginpass()){
			bool=false;
		}
		if(!validateVerifyCode()){
			bool=false;
		}
		if(!validateEmail()){
			bool=false;
		}
		return bool;
	});
});

/**
 * 登录名校验方法
 * @param ele
 * @returns
 */
function validateLoginname(){
	var id="loginname";
	var value=$("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("用户名不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2. 长度校验
	 */
	if(value.length<3 || value.length>20){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("用户名长度需在3~20之间");
		showError($("#"+id+"Error"));
		alert('xxx');
		return false;
	}
	$.ajax({
		url:"/mystore/UserServlet",
		data:{method:"ajaxValidateLoginname",loginname:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				$("#"+id+"Error").text("用户名已存在");
				showError($("#"+id+"Error"));
				return false;
			}
		}
	});
	return true;
}
/**
 * 登录密码校验
 * @param ele
 * @returns
 */
function validateLoginpass(){
	var id="loginpass";
	var value=$("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("密码不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2. 长度校验
	 */
	if(value.length<3 || value.length>20){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("密码长度需在3~20之间");
		showError($("#"+id+"Error"));
		return false;
	}
	
	return true;
	
}
/**
 * 确认密码校验
 * @returns
 */
function validateReloginpass(){
	var id="reloginpass";
	var value=$("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("确认密码不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2. 是否一样校验
	 */
	if(value!= $("#loginpass").val()){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("两次输入不一致");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}
function validateEmail(){
	var id="email";
	var value=$("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("邮箱不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * email格式校验
	 */
	if(!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)){
		$("#"+id+"Error").text("邮箱格式错误");
		showError($("#"+id+"Error"));
		return false;
	}
	$.ajax({
		url:"/mystore/UserServlet",
		data:{method:"ajaxValidateEmail",email:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				$("#"+id+"Error").text("该邮箱已被注册");
				showError($("#"+id+"Error"));
				return false;
			}
		}
	});
	return true;
}
/**
 * 验证码校验
 * @returns
 */
function validateVerifyCode(){
	var id="verifyCode";
	var value=$("#"+id).val();
	/**
	 * 1. 非空校验
	 */
	if(!value){
		/**
		 * 获取对应的label
		 * 添加错误信息
		 * 显示label
		 */
		$("#"+id+"Error").text("验证码不能为空");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 2. 长度校验
	 */
	if(value.length!=4){
		$("#"+id+"Error").text("验证码错误");
		showError($("#"+id+"Error"));
		return false;
	}
	/**
	 * 验证码是否争取
	 * @param ele
	 * @returns
	 */
	$.ajax({
		url:"/mystore/UserServlet",
		data:{method:"ajaxValidateVerifyCode",verifyCode:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				$("#"+id+"Error").text("验证码错误");
				showError($("#"+id+"Error"));
				return false;
			}
		}
	});
	return true;
}
function showError(ele){
	var text = ele.text();
	if(!text){
		ele.css("display","none");
	}else{
		ele.css("display","");
	}
};

function _hyz(){
	
	var img = document.getElementById("imgVerifyCode");
	//给出一个参数，目的是抵消浏览器的缓存作用
	img.src="/mystore/VerifyCodeServlet?a="+new Date().getTime();
	
};
