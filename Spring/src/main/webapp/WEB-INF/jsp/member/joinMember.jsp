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

    <%-- 다음 주소 찾기 API CDN --%>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

    <%-- 회원가입용 스크립트 파일 호출 --%>
    <script src="/resource/res/js/joinmember.js"></script>
</head>
<body>

<%
    String name = request.getParameter("name");
    String phone = request.getParameter("phone");
%>
    <div class="sidenav">
        <div class="login-main-text">
            <h2>Application<br> Register Page</h2>
            <p>Register from here to access.</p>
            <p>Return To <a href="/index.do">Main Page</a></p>
        </div>
    </div>

    <div class="main">
        <div class="col-md-6 col-sm-12">
            <div class="login-form">
                <form name="newMember" action="/member/processJoinMember.do" method="post">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                        <strong>아이디</strong>
                        <input type="text" class="form-control" name='id' id="id" placeholder="ID" required>
                        </div>

                        <div class="col-md-4">
                            <strong>&nbsp;</strong>
                            <input type="button" class="btn btn-danger btn-block" onclick="idCheck()" value="중복 조회">
                        </div>
                    </div>

                    <div class="form-group">
                        <strong>비밀번호</strong>
                        <input type="password" id="password" name='password' class="form-control" placeholder="Password" required>
                    </div>

                    <div class="form-group">
                        <strong>비밀번호 확인</strong>
                        <input type="password" id="password_check" name='password_confirm' class="form-control" placeholder="Password" required>
                    </div>

                    <div class="form-group">
                        <strong>성명</strong>
                        <input type="text" name='name' id="name" class="form-control" placeholder="name" value="<%=name%>" readonly>
                    </div>

                    <div class="form-group">
                        <strong>성별</strong>
                        <br>
                        <input name="gender" type="radio" value="남성" /> 남성&nbsp;&nbsp;&nbsp;
                        <input name="gender" type="radio" value="여성" /> 여성
                    </div>

                    <div class="form-group">
                        <strong>생년월일</strong>
                        <br>
                        <input type="hidden" name="birth">
                        <input type="text" id="birthyy" name="birthyy" maxlength="4" placeholder="YYYY" size="6" required>&nbsp;&nbsp;&nbsp;
                        <select name="birthmm" id="birthmm">
                            <option value="">월</option>
                            <option value="01">1</option>
                            <option value="02">2</option>
                            <option value="03">3</option>
                            <option value="04">4</option>
                            <option value="05">5</option>
                            <option value="06">6</option>
                            <option value="07">7</option>
                            <option value="08">8</option>
                            <option value="09">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>&nbsp;&nbsp;&nbsp;
                        <input type="text" id="birthdd" name="birthdd" maxlength="2" placeholder="DD" size="4" required>
                    </div>

                    <div class="form-group">
                        <strong>이메일</strong>
                        <br>
                        <input type="hidden" name="mail">
                        <input type="text" name="mail1" id="mail1" maxlength="15" size="10" required>&nbsp;&nbsp;@&nbsp;&nbsp;
                        <select name="mail2">
                            <option>naver.com</option>
                            <option>daum.net</option>
                            <option>gmail.com</option>
                            <option>nate.com</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <strong>전화번호</strong>
                        <br>
                        <input name="phone" type="text" id="phone" class="form-control" maxlength="11" value="<%=phone%>" readonly>
                    </div>

                    <div class="form-group">
                        <strong>주소</strong>
                        <br>
                        <input name="address" type="hidden" class="form-control">
                        <input name="address1" id="address1" type="text" class="form-control" placeholder="마우스 클릭 시 주소입력 창이 나타납니다." onclick="findAddress()" required>
                        <input name="address2" id="address2" type="text" class="form-control" required>
                    </div>

                    <br>

                    <div class="form-group">
                        <strong>이메일 수신 동의</strong>
                        <br>
                        <input name="email_agree" type="radio" value="동의" /> 동의&nbsp;&nbsp;&nbsp;
                        <input name="email_agree" type="radio" value="미동의" /> 미동의
                    </div>

                    <div class="form-group">
                        <strong>SMS 수신 동의</strong>
                        <br>
                        <input name="sms_agree" type="radio" value="동의" /> 동의&nbsp;&nbsp;&nbsp;
                        <input name="sms_agree" type="radio" value="미동의" /> 미동의
                    </div>

                    <br>

                    <input onclick="checkForm()" id="join" class="btn btn-black" value="회원가입" size="5" disabled>
                    <input type="reset" class="btn btn-secondary " value="취소 " onclick="location.href='/'" >
                </form>
            </div>
        </div>
    </div>
</body>
</html> 