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
<c:set var="sum" value="${0}"/>
<h2>포인트 적립순위 조회 페이지</h2>
<br>
<div class="table-responsive">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>순위</th>
            <th>아이디</th>
            <th>이름</th>
            <th>전화번호</th>
            <th>포인트 합계</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="point" items="${PointRanking}" varStatus="status">
            <tr>
                <td style="text-align: center;">${point.point_rank}</td>
                <td style="text-align: center;">${point.ID}</td>
                <td style="text-align: center;">${point.NAME}</td>
                <td style="text-align: center;">${point.PHONE}</td>
                <td style="text-align: center;">${point.TOTAL}P</td>
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