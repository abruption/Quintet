<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<html>
<head>
	<%-- 반응형 Web 제작을 위한 Code --%>
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<%-- BootStrap CDN 추가 --%>
	<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

	<%-- CSS 적용 --%>
	<link rel="stylesheet" type="text/css" href="/resource/res/css/member.css">

	<%-- JQuery CDN 추가	--%>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

	<%-- 회원정보 수정용 스크립트 파일 호출 --%>
	<script src="/resource/res/js/updatemember.js"></script>

	<script type="text/javascript">
		function init() {
			var mail = "${member.mail}";
			$("#mail1").val(mail.split('@')[0]);
			$("#mail2").val(mail.split('@')[1]);

			var gender = "${member.gender}";
			if(gender == "남성"){
				$("#male").prop("checked", true);
			} else{
				$("#female").prop("checked", true);
			}

			var birth = "${member.birth}";
			$("#birthyy").val(birth.substring(0, 4));
			$("#birthmm").val(birth.substring(4, 6));
			$("#birthdd").val(birth.substring(6, 8));

			var email_agree = "${member.email_agree}";
			if(email_agree == "동의"){
				$("#email_agree").prop("checked", true);
			} else {
				$("#email_disagree").prop("checked", true);
			}

			var sms_agree = "${member.sms_agree}";
			if(sms_agree == "동의"){
				$("#sms_agree").prop("checked", true);
			} else {
				$("#sms_disagree").prop("checked", true);
			}

		}
	</script>

<title>회원 수정</title>
</head>
<body onload="init()">
	<div class="sidenav">
		<div class="login-main-text">
			<h2>Member Information<br> Management Page</h2>
			<br>
			<p>Return To <a href="/index.do">Main Page</a></p>
		</div>
	</div>

	<div class="main">
		<div class="col-md-6 col-sm-12">
			<div class="login-form">
				<form name="EditMember" class="form-horizontal" action="/member/processUpdateMember.do" method="post">
				<div class="form-group">
					<strong>아이디</strong>
					<input name="id" id="id" type="text" class="form-control" placeholder="id" value="<c:out value='${member.id }'/>" readonly >
				</div>


				<div class="form-group">
					<strong>비밀번호</strong>
					<input type="password" id="password" name='password' class="form-control" placeholder="Password" value="<c:out value="${member.password}" />" >
				</div>

				<div class="form-group">
					<strong>성명</strong>
					<input type="text" id="name" name='name' class="form-control" placeholder="name" value="<c:out value='${member.name }'/>" readonly>
				</div>

				<div class="form-group">
					<strong>성별</strong>
					<br>
					<input name="gender" id="male" type="radio" value="남성" /> 남성&nbsp;&nbsp;&nbsp;
					<input name="gender" id="female" type="radio" value="여성" /> 여성
				</div>

				<div class="form-group">
					<strong>생년월일</strong>
					<br>
					<input type="hidden" name="birth">
					<input type="text" id="birthyy" name="birthyy" maxlength="4" placeholder="YYYY" size="6">&nbsp;&nbsp;&nbsp;
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
					<input type="text" name="birthdd" id="birthdd" maxlength="2" placeholder="DD" size="4">
				</div>

				<div class="form-group">
					<strong>이메일</strong>
					<br>
					<input type="hidden" name="mail">
					<input type="text" id="mail1" name="mail1" maxlength="15" size="10">&nbsp;&nbsp;@&nbsp;&nbsp;
					<select name="mail2" id="mail2">
						<option>naver.com</option>
						<option>daum.net</option>
						<option>gmail.com</option>
						<option>nate.com</option>
					</select>
				</div>

				<div class="form-group">
					<strong>전화번호</strong>
					<br>
					<input name="phone" id="phone" type="text" class="form-control" value="<c:out value='${member.phone }'/>" readonly>
				</div>

				<div class="form-group">
					<strong>주소</strong>
					<br>
					<input name="address" id="address" type="text" class="form-control" value="<c:out value='${member.address }'/>">
				</div>

				<div class="form-group">
					<strong>이메일 수신 동의</strong>
					<br>
					<input name="email_agree" id="email_agree" type="radio" value="동의" /> 동의&nbsp;&nbsp;&nbsp;
					<input name="email_agree" id="email_disagree" type="radio" value="미동의" /> 미동의
				</div>

				<div class="form-group">
					<strong>SMS 수신 동의</strong>
					<br>
					<input name="sms_agree" id="sms_agree" type="radio" value="동의" /> 동의&nbsp;&nbsp;&nbsp;
					<input name="sms_agree" id="sms_disagree" type="radio" value="미동의" /> 미동의
				</div><br>

				<div class="form-group">
					<strong>가용포인트 : </strong><a href="/point/detailPoint.do?id=${member.id}" style="text-decoration: none">${TotalPoint}P</a>
				</div>

				<br>

				<input onclick="checkForm()" id="join" class="btn btn-success" value="회원정보 수정" size="6">
				<a href="#" onclick="DeleteMember(); return false;" class="btn btn-dark">회원탈퇴</a>
			</form>
			</div>
		</div>
	</div>
</body>
</html>
