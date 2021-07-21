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

    <%-- CSS 적용 --%>
    <link rel="stylesheet" type="text/css" href="/resource/res/css/member.css">

    <%-- 로그인 스크립트 파일 호출 --%>
    <script src="/resource/res/js/loginmember.js"></script>

</head>
<body>
    <div class="sidenav">
        <div class="login-main-text">
            <h2>Application<br> Login Page</h2>
            <p>Login from here to access.</p>
            <p>Return To <a href="/index.do">Main Page</a></p>
        </div>
    </div>


    <div class="main">
        <div class="col-md-6 col-sm-12">
            <div class="login-form">
                <div class="form-group">
                    <label>아이디</label>
                    <input type="text" name="id" id="id" class="form-control" placeholder="아이디">
                </div>
                <div class="form-group">
                    <label>비밀번호</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호">
                </div>

                <input type="button" class="btn btn-black" value="로그인" onclick="loginCheck()">
                <input type="button" class="btn btn-secondary" onclick="location.href='/member/checkMember.do'" value="회원가입">
            </div>
        </div>
    </div>
</body>
</html> 