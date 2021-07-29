<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
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

    <script type="text/javascript">
        function checkEvent(event, use_point, end_date) {
            var data = {
                event : event
            }

            $.ajax({
                url: '/CheckEvent',
                type: 'POST',
                data: data,

                success: function(result){
                    if(result == 1){
                        alert('이미 참여한 이벤트입니다.');
                    } else {
                        JoinEvent(event, use_point, end_date);
                    }
                }
            });
        }

        function JoinEvent(event, use_point, end_date) {
            var event = event;
            var use_point = use_point;
            var now = new Date();
            var end_date = new Date(end_date);

            if (now.getTime() <= end_date.getTime()) {
                if (confirm('이벤트에 참여하시려면 확인 버튼을 눌러주세요.') == true) {
                    alert('이벤트에 정상적으로 응모되었습니다.');
                    location.href = '/event/JoinEvent.do?event=' + event + '&use_point=' + use_point;
                } else {
                    alert('해당 이벤트는 종료된 이벤트입니다.');
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
    <h2>이벤트 목록</h2>
    <br>
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
                        <td><a href="#" onclick="checkEvent('${event.TITLE}', '${event.USE_POINT}', '${event.END_DATE}'); return false;">${event.TITLE}</a></td>
                        <td>${event.START_DATE}</td>
                        <td>${event.END_DATE}</td>
                        <td>${event.USE_POINT}P</td>
                        <td>${event.WINNER}명</td>

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