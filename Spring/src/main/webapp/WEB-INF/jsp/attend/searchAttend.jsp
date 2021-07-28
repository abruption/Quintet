<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%-- 반응형 Web 제작을 위한 Code --%>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%-- BootStrap CDN 추가 --%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

    <%-- Google jQuery Library --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

    <%-- jQuery HTML Table Search Plugin --%>
    <script src="/resource/res/js/html-table-search.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $('table.search-table').tableSearch({
                searchText:'<b>' + 'Search : ' + '</b>',
                searchPlaceHolder:' '
            });
        });
    </script>


    <style>
        #pagination a {
            display:inline-block;
            margin-right:5px;
            cursor:pointer;
        }
        th {
            text-align: center;
        }
        td {
            text-align: center;
        }
    </style>
    <meta charset="UTF-8">
    <title>title</title>
</head>
<body>
<br>
<h2>관리자용 출석현황 조회 페이지</h2>
<br>
    <div class="table-responsive">
        <table class="search-table table table-bordered table-hover">
            <thead>
            <tr>
                <th>아이디</th>
                <th>회원명</th>
                <th>전화번호</th>
                <th>적립포인트</th>
                <th>출석일자</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="member" items="${MemberList}" varStatus="status">
                <tr>
                    <td>${member.ID}</td>
                    <td>${member.NAME}</td>
                    <td>${member.PHONE}</td>
                    <c:forEach var="point" items="${PointRanking}" varStatus="status">
                        <c:if test="${member.ID eq point.ID}">
                            <td><a href="/point/detailPoint.do?id=${point.ID}" style="text-decoration: none;">${point.TOTAL}P</a></td>
                        </c:if>
                    </c:forEach>
                    <c:forEach var="attend" items="${AttendCount}" varStatus="status">
                        <c:if test="${attend.ID eq member.ID}">
                            <td class="search"><a href="/attend/detailAttend.do?id=${attend.ID}" style="text-decoration: none;">${attend.COUNT}일</a></td>
                        </c:if>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    <br>
    <div style="float: right">
        <input type="button" value="뒤로가기" class="btn-black" onclick="history.back()">
        <input type="button" value="메인 화면" class="btn-black" onclick="location.href='/'">
    </div>
    </div>
</body>
</html> 