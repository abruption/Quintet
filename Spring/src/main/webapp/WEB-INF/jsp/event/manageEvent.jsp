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
    <% String title = request.getParameter("title"); %>
    <script type="text/javascript">
        function DrawEvent(title, winner, end_date, use_point){
            var now = new Date();
            var end_date = new Date(end_date);
            if(confirm(title + ' 이벤트를 추첨하시겠습니까?') == true){
                if (now.getTime() < end_date.getTime()){
                    alert('아직 종료되지 않은 이벤트입니다.');
                } else {
                    location.href = '/event/drawEvent.do?title=' + title + '&winner=' + winner + '&use_point=' + use_point;
                }
            }
        }
    </script>
    <style>
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
    <h2>관리자용 이벤트 관리 페이지</h2> <br>
<div class="table-responsive">
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>이벤트명</th>
            <th>이벤트 시작일</th>
            <th>이벤트 종료일</th>
            <th>이벤트 참여 포인트 금액 (P)</th>
            <th>당첨자 수 (명)</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="event" items="${EventList}" varStatus="status">
            <tr>
                <td><a id="title" href="#" onclick="DrawEvent('${event.TITLE}', '${event.WINNER}', '${event.END_DATE}', '${event.USE_POINT}'); return false;">${event.TITLE}</a></td>
                <td>${event.START_DATE}</td>
                <td>${event.END_DATE}</td>
                <td>${event.USE_POINT}P</td>
                <td>${event.WINNER}명</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>


<div style="float: right">
    <input type="button" class="btn btn-primary" value="이벤트 등록하기" size="5" onclick="location.href='/event/AddEvent.do'">
</div>
<br><br>
<div style="float: right">
    <input type="button" value="뒤로가기" class="btn-black" onclick="history.back()">
    <input type="button" value="메인 화면" class="btn-black" onclick="location.href='/'">
</div>
</body>
</html> 