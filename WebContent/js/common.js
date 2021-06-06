function _change() {
	$("#vCode").attr("src", "/mystore/VerifyCodeServlet?" + new Date().getTime());
}