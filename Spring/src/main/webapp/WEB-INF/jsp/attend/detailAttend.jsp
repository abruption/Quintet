<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%-- 반응형 Web 제작을 위한 Code --%>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%-- BootStrap CDN 추가 --%>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <%-- Google jQuery Library --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <%-- 포인트 상세조회용 스크립트 파일 호출 --%>
    <script src="/resource/res/js/detailPoint.js"></script>
    <style>
        #pagination a {
            display:inline-block;
            margin-right:5px;
            cursor:pointer;
        }
    </style>
    <meta charset="UTF-8">
    <title>title</title>
</head>
<body>
<br>
    <h2>출석현황 조회 페이지</h2>
    <br>
    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>아이디</th>
                <th>이름</th>
                <th>전화번호</th>
                <th>출석 날짜</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="attend" items="${detailAttend}" varStatus="status">
                <tr>
                    <td style="text-align: center;">${attend.ID}</td>
                    <td style="text-align: center;">${attend.NAME}</td>
                    <td style="text-align: center;">${attend.PHONE}</td>
                    <td style="text-align: center;">${attend.ATT_DATE}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <br><br>
    <div style="float: right">
        <input type="button" value="뒤로가기" class="btn-black" onclick="history.back()">
        <input type="button" value="메인 화면" class="btn-black" onclick="location.href='/'">
    </div>
</body>
</html> 