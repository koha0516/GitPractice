<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dto.Account" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
		<tr>
			<th>ID</th>
			<th>氏名</th>
			<th>メール</th>
			<th>年齢</th>
			<th>性別</th>
			<th>TEL</th>
		</tr>
	<%
	List<Account> list = (ArrayList<Account>)request.getAttribute("account");
	for(Account s : list) {
	%>
		<tr>
			<td><%=s.getId() %></td>
			<td><%=s.getName() %></td>
			<td><%=s.getMail() %></td>
			<td><%=s.getAge() %></td>
			<td><%=s.getGender() %></td>
			<td><%=s.getTel() %></td>
		</tr>
	<%} %>
	</table>
	
	<a href="./">戻る</a>


</body>
</html>