<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%-- 반응형 Web 제작을 위한 Code --%>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%--BootStrap CDN 추가--%>
    <link rel="stylesheet" type="text/css" href="/resource/res/css/product.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

    <%-- Google jQuery Library --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        function Logout(){
            location.href="/member/LogoutMember.do";
            alert('로그아웃 되었습니다.');
        }

        function Login() {
            location.href="/member/LoginMember.do";
            alert('로그인 화면으로 이동합니다.');
        }

        function Check_Attendance() {
            var id = '<%=(String) session.getAttribute("userId")%>';
            var data = {id : id}
            $.ajax({
                url: '/Check_Attendance',
                type: 'POST',
                data: data,

                success: function(result){
                    if(result != 'Fail'){
                        Attendance();
                    } else
                        alert('이미 출석체크를 하셨습니다.');
                }
            });
        }

        function Attendance() {
            var id = '<%=(String) session.getAttribute("userId")%>';
            var data = {id : id}
            $.ajax({
                url: '/Attendance',
                type: 'POST',
                data: data,

                success: function(result){
                    alert('출석체크 포인트가 지급되었습니다.');
                    if(result == "Bonus"){
                        alert('3일 연속 출석으로 추가 2,000P가 지급되었습니다.');
                    }
                }
            });
        }
    </script>



    <meta charset="UTF-8">
    <title>SHOP 가나다라</title>
</head>
<body>
    <%@ include file="header.jsp"%>

    <main>
        <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
            <div class="col-md-5 p-lg-5 mx-auto my-5">
                <c:choose>
                    <c:when test="${empty userId}">
                        <h1 class="display-4 fw-normal">Punny headline</h1>
                        <p class="lead fw-normal">And an even wittier subheading to boot. Jumpstart your marketing efforts with this example based on Apple’s marketing pages.</p>
                        <a class="btn btn-outline-secondary" href="#">Coming soon</a>
                    </c:when>
                    <c:otherwise>
                        <h1 class="display-4 fw-normal">출석 체크</h1>
                        <span class="lead fw-normal">출석 체크시 기본 1,000포인트 적립.</span><br>
                        <p class="lead fw-normal">3일 연속 출석체크시 추가 2,000포인트 적립.</p>
                        <p class="lead fw-normal">단, 1일 1회 한정</p>
                        <a class="btn btn-outline-secondary" href='#' onclick="Check_Attendance(); return false;">출석 체크</a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="product-device shadow-sm d-none d-md-block"></div>
            <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
        </div>
    </main>



    <%@ include file="footer.jsp"%>
</body>
</html>