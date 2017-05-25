<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://192.168.1.184/mytag" prefix="mytag" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>我是第三个localhost</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>/js/recorder.js"></script>
<script src="<%=basePath%>/js/recorderWorker.js"></script>
</head>


<body>
    <mytag:Test>1111</mytag:Test>
    <form  id="form" action="<%=basePath%>test/change.do" method="post"><input type="text" name="type"/></form>
	<a id="mydiv"
		style="width: 200px; height: 200px; background: #ddd; font-size: 50px"
		title='0' onclick="send();">ajax</a>
	<a id="mydiv1"
		style="width: 200px; height: 200px; background: #ddd; font-size: 50px"
		title='0' onclick="clickMe();">form</a>
		<br /> <span id="message" style="font-size: 50px"></span>
</body>
<script type="text/javascript">
	function send() {
		 $.ajax({
				type: "POST",
				url: "<%=basePath%>/test/json.do",
				async:false, //这是重要的一步，防止重复提交的
			cache : false,
			success : function(response){
						console.log(response);
						var data=JSON.parse(response) 
						if(data.code==1){
							window.location.href="http://www.baidu.com";
						}

				}
			});
	}
	function clickMe(){
		$("#form").submit();
	}
</script>
</html>
