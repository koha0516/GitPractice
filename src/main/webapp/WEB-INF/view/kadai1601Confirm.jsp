<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Account" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>下記の内容で登録します。よろしいですか？</p>
	<%
		Account account = (Account)session.getAttribute("input_data");
	%>
	名前：<%=account.getName() %><br>
	メール：<%=account.getMail() %><br>
	年齢：<%=account.getAge() %>
	性別：<%=account.getGender() %>
	電話番号：<%=account.getTel() %>
	パスワード：********<br>
	<a href="Kadai1601Execute">OK</a><br>
	<a href="RegisterFormServlet">戻る</a>
</body>
</html>