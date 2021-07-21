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
    <link rel="stylesheet" type="text/css" href="/resource/res/css/checkmember.css">

    <script type="text/javascript">
        function memberCheck(){
            var patt = new RegExp("[0-9]{2,3}-[0-9]{3,4}-[0-9]{3,4}");

            if( !patt.test( $("#phone").val()) ){
                alert("전화번호를 정확히 입력하여 주십시오.");
                return false;
            }
            var name = $("#name").val();
            var phone = $("#phone").val();
            var data = { phone : phone }

            $.ajax({
                url: '/Member_Check',
                type: 'POST',
                data: data,

                success: function(result){
                    if(result != 'fail'){
                        if(confirm('회원이 아닙니다.\n회원가입을 진행하시려면 확인을 눌러주세요.') == true){
                            location.href='/member/JoinMember.do?name=' + name + '&phone=' + phone;
                        }
                    } else
                        alert('이미 가입한 번호입니다.')
                }
            });
        }
    </script>
</head>
<body>
    <div class="sidenav">
        <div class="login-main-text">
            <h2>Application<br> Register Check Page</h2>
            <br>
            <p>Return To <a href="/index.do">Main Page</a></p>
        </div>
    </div>

    <div class="main">
        <div class="container login-container">
            <div class="row">
                <div class="col-md-100 login-form-1">
                    <h3>회원가입 여부 확인</h3>

                    <div class="form-group">
                        <input type="text" id="name" class="form-control" placeholder="이름을 입력하세요." value="" />
                    </div>
                    <div class="form-group">
                        <input type="text" id="phone" name="phone" class="form-control" placeholder="010-0000-0000" value="" pattern="(010)-\d{3,4}-\d{4}" maxlength="13" title="010-0000-0000"/>
                    </div>
                    <div class="form-group">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" class="btnSubmit" value="조회" onclick="memberCheck()"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 