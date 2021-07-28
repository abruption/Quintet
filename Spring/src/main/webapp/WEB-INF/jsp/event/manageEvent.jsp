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
    <h2>관리자용 이벤트 관리 페이지</h2> <br>
<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
        <form action="/event/processManageEvent.do" method="post">
            <div class="form-group">
                <strong>이벤트명</strong>
                <br>
                <input name="phone" type="text" class="form-control" >
            </div><br>

            <div class="col-md-6 mb-3">
                <strong>이벤트 시작일자</strong>
                <br>
                <input name="phone" type="date" class="form-control" >
            </div><br>

            <div class="col-md-6 mb-3">
                <strong>이벤트 종료일자</strong>
                <br>
                <input name="end_date" type="date" class="form-control">
            </div><br>

            <div class="col-md-6 mb-3">
                <strong>이벤트 참여포인트</strong>
                <br>
                <input name="usepoint" type="number"  class="form-control" value="0">
            </div><br>

            <div class="col-md-6 mb-3">
                <strong>이벤트 당첨자 수</strong>
                <br>
                <input name="winner" type="number"  class="form-control" value="0">
            </div>

            <br>
            <input onclick="checkForm()" id="join" class="btn btn-primary" value="등록" size="5">
            <input type="reset" class="btn btn-secondary " value="취소 " onclick="location.href='/'" >
        </form>
        </div>
    </div>
</div>
</body>
</html> 