<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web1</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="Styles/Styles.css" rel="stylesheet" type = "text/css">
</head>
<body>
<td id = "answers" colspan="2">
    <table id="result_table" align="center" border="1" width="80%">
        <tr>
            <th width="15%">
                X
            </th>
            <th width="15%">
                Y
            </th>
            <th width="15%">
                R
            <th width="20%">
                Hit Result
            </th>
        </tr>
        <jsp:useBean id="resulList" scope="session" class="Results.ResultList"/>
        <c:forEach var="result" items="${resultList.resultList}">
            <tr>
                <td> ${result.x}</td>
                <td> ${result.y}</td>
                <td> ${result.r}</td>
                <td> ${result.result} </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4" align="center">
                <input id = "back" type="submit" value="Назад" onclick="location.href = 'http://localhost:8080/WEB_2_Web_exploded/Client/Main.jsp'">
            </td>
        </tr>
    </table>
</body>
</html>
