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
        function checkForm() {
            if ($("#title").val() == '' || $("#title").val() == null) {
                alert('이벤트명을 입력해주세요.');
                return false;
            }
            if ($("#start_date").val() == '' || $("#start_date").val() == null) {
                alert('이벤트 시작일을 입력해주세요.');
                return false;
            }
            if ($("#end_date").val() == '' || $("#end_date").val() == null) {
                alert('이벤트 종료일을 입력해주세요.');
                return false;
            }
            if ($("#use_point").val() == 0 || $("#use_point").val() == null) {
                alert('이벤트 참여 포인트를 입력해주세요.');
                return false;
            }
            if ($("#winner").val() == 0 || $("#winner").val() == null) {
                alert('이벤트 당첨자 수를 입력해주세요.');
                return false;
            }
            addEvent();
        }

        function addEvent(){
            var title = $("#title").val();
            var start_date = $("#start_date").val();
            var end_date = $("#end_date").val();
            var use_point = $("#use_point").val();
            var winner = $("#winner").val();

            var data = {
                title : title,
                start_date : start_date,
                end_date : end_date,
                use_point : use_point,
                winner : winner
            }

            $.ajax({
                url: '/AddEvent',
                type: 'POST',
                data: data,

                success: function(){
                    alert('이벤트 등록이 완료되었습니다.');
                    location.href='/';
                }
            });

        }
    </script>
    <meta charset="UTF-8">
    <title>title</title>
</head>
<body>
<br>
<h2>관리자용 이벤트 등록 페이지</h2> <br>

<div class="main">
    <div class="col-md-6 col-sm-12">
        <div class="login-form">
            <form name="manageEvent" method="post">
                <div class="form-group">
                    <strong>이벤트명</strong>
                    <br>
                    <input id="title" type="text" class="form-control" >
                </div><br>

                <div class="col-md-6 mb-3">
                    <strong>이벤트 시작일자</strong>
                    <br>
                    <input id="start_date" type="datetime-local" class="form-control" >
                </div><br>

                <div class="col-md-6 mb-3">
                    <strong>이벤트 종료일자</strong>
                    <br>
                    <input id="end_date" type="datetime-local" class="form-control">
                </div><br>

                <div class="col-md-6 mb-3">
                    <strong>이벤트 참여포인트</strong>
                    <br>
                    <input id="use_point" type="number"  class="form-control" value="0">
                </div><br>

                <div class="col-md-6 mb-3">
                    <strong>이벤트 당첨자 수</strong>
                    <br>
                    <input id="winner" type="number"  class="form-control" value="0">
                </div>

                <br>
                <input onclick="checkForm()" id="submit" class="btn btn-primary" value="등록" size="5">
                <input type="reset" class="btn btn-secondary " value="취소 " onclick="location.href='/'" >
            </form>
        </div>
    </div>
</div>
</body>
</html> 