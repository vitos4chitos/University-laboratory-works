<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Web1</title>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <link href="Styles/Styles.css" rel="stylesheet" type = "text/css">
</head>
<body>
<table class = "table main">
    <tr>
        <td colspan="2">
            <h2 class = "header main">Работа Козлова Виктора Николаевича<br>Студента группы P3210<br>
                Номер варианта:10728</h2>
        </td>
    </tr>
    <tr>
        <td id = "inputxyr">
            <form method="post" action="">
                <table class = "table data">
                    <tr>
                        <td colspan="3"><h class="header data">Введите данные</h></td>
                    </tr>
                    <tr class="values">
                        <td>Значение х:</td>
                        <td>Значение y:</td>
                        <td>Значение R:</td>
                    </tr>
                    <tr>
                        <td>
                            <table class = "table x">
                                <tr>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="-5" class = "radio x" required  onclick="swipe()">-5
                                        </label>

                                    </td>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="-4" class = "radio x" required onclick="swipe()">-4
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="-3" class = "radio x" required onclick="swipe()">-3
                                        </label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="-2" class = "radio x" required onclick="swipe()">-2
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="-1" class = "radio x" required onclick="swipe()">-1
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="0" class = "radio x" required onclick="swipe()">0
                                        </label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="1" class = "radio x" required onclick="swipe()">1
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="2" class = "radio x" required onclick="swipe()">2
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label x">
                                            <input type = "radio" name="x" value="3" class = "radio x" required onclick="swipe()">3
                                        </label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td>
                            <p><input type="text" class = "input" required maxlength = "12"></p>
                            <p>(-5..3)</p>
                        </td>
                        <td>
                            <table class = "table r">
                                <tr>
                                    <td>
                                        <label class = "label r">
                                            <input type = "checkbox" name="r" value="1" class = "radio r" onclick="swipe()">1
                                        </label>

                                    </td>
                                    <td>
                                        <label class = "label r">
                                            <input type = "checkbox" name="r" value="1.5" class = "radio r" onclick="swipe()">1.5
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label r">
                                            <input type = "checkbox" name="r" value="2" class = "radio r" onclick="swipe()">2
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label r">
                                            <input type = "checkbox" name="r" value="2.5" class = "radio r" onclick="swipe()">2.5
                                        </label>
                                    </td>
                                    <td>
                                        <label class = "label r">
                                            <input type = "checkbox" name="r" value="3" class = "radio r"  onclick="swipe()">3
                                        </label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3"><input type="submit" value="Проверить" onclick="test()"></td>
                    </tr>
                </table>
            </form>
        </td>
        <td id="picture" align="center">
            <svg height= "300px" width= "300px" xmlns="http://www.w3.org/2000/svg" id = "svg">
                <line stroke="white" x1="0" x2="300" y1="150" y2="150"></line>
                <line stroke="white" x1="150" x2="150" y1="0" y2="300"></line>

                <polygon fill="white" points="150,0 144,15 156,15" stroke="white"></polygon>
                <polygon fill="white" points="300,150 285,156 285,144" stroke="white"></polygon>

                <line stroke="white" x1="200" x2="200" y1="155" y2="145"></line>
                <line stroke="white" x1="250" x2="250" y1="155" y2="145"></line>

                <line stroke="white" x1="50" x2="50" y1="155" y2="145"></line>
                <line stroke="white" x1="100" x2="100" y1="155" y2="145"></line>

                <line stroke="white" x1="145" x2="155" y1="100" y2="100"></line>
                <line stroke="white" x1="145" x2="155" y1="50" y2="50"></line>

                <line stroke="white" x1="145" x2="155" y1="200" y2="200"></line>
                <line stroke="white" x1="145" x2="155" y1="250" y2="250"></line>

                <polygon points="150,150 250,150 150,250" stroke="white" fill-opacity="0.3" class = "figure triangle"></polygon>

                <polygon fill-opacity="0.3" points="150,150 50,150 50,250 150,250"
                         stroke="white" class = "figure square"></polygon>
                <path d="M 250 150 A 100 100, 90, 0, 0, 150 50 L 150 150 Z"  fill-opacity="0.3"
                      stroke="white" class = "figure circle"></path>

                <text fill="white" x="195" y="140">R/2</text>
                <text fill="white" x="252" y="140">R</text>

                <text fill="white" x="40" y="140">-R</text>
                <text fill="white" x="90" y="140">-R/2</text>

                <text fill="white" x="160" y="105">R/2</text>
                <text fill="white" x="160" y="45">R</text>

                <text fill="white" x="160" y="205">-R/2</text>
                <text fill="white" x="160" y="265">-R</text>

                <text fill="white" x="288" y="140">X</text>
                <text fill="white" x="160" y="10">Y</text>
            </svg>
        </td>
    </tr>
    <tr>
        <td id = "answers" colspan="2">
            <table id="result_table" align="center" border="1" width="80%" >
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
                        <input type="submit" value="Сброс" onclick="reset()">
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
<script src="Scripts/Scripts.js"></script>
</html>