function checkForm() {
    if ($("#password").val() == '' || $("#password").val() == null) {
        alert("비밀번호를 입력하세요.");
        return false;
    }
    if ($("#password_check").val() == '' || $("#password_check").val() == null) {
        alert("비밀번호 확인란을 입력하세요.");
        return false;
    }
    if ($("#password").val() != $("#password_check").val()) {
        alert("비밀번호를 동일하게 입력하세요.");
        return false;
    }
    if ($('input:radio[name=gender]').is(':checked') == false) {
        alert("성별을 선택하세요.");
        return false;
    }
    if ($("#birthyy").val() == '' || $("#birthyy").val() == null) {
        alert("생년월일 연도를 입력하세요.");
        return false;
    }
    if(!$('#birthmm > option:selected').val()) {
        alert("생년월일 월를 선택하세요.");
        return false;
    }
    if ($("#birthdd").val() == '' || $("#birthdd").val() == null) {
        alert("생년월일 일자를 입력하세요.");
        return false;
    }
    if ($("#mail1").val() == '' || $("#mail1").val() == null) {
        alert("이메일 주소를 입력하세요.");
        return false;
    }
    if ($("#address2").val() == '' || $("#address2").val() == null) {
        alert("주소를 입력하세요.");
        return false;
    }
    if ($('input:radio[name=email_agree]').is(':checked') == false) {
        alert("이메일 수신동의 여부를 선택하세요.");
        return false;
    }
    if ($('input:radio[name=sms_agree]').is(':checked') == false) {
        alert("SMS 수신동의 여부를 선택하세요.");
        return false;
    }

    fn_addMember();
}

function fn_addMember() {
    document.newMember.action = '/member/processJoinMember.do';
    document.newMember.address.value = document.newMember.address1.value + " " + document.newMember.address2.value;
    document.newMember.birth.value = document.newMember.birthyy.value + document.newMember.birthmm.value + document.newMember.birthdd.value
    document.newMember.mail.value = document.newMember.mail1.value + "@" + document.newMember.mail2.value
    document.newMember.submit();
}

function idCheck(){
    var id = $("#id").val();
    var data = {id : id}

    $.ajax({
        url: '/ID_Check',
        type: 'POST',
        data: data,

        success: function(result){
            if(result != 'fail'){
                if(confirm('사용가능한 아이디입니다.\n사용하시려면 확인을 눌러주세요.') == true){
                    $('#id').attr("readonly", true);
                    $('#join').attr("disabled", false);
                }
            } else
                alert('사용중인 아이디입니다. 다른 아이디를 입력해주세요.')
        }
    });
}

function findAddress(){
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("address2").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("address1").value = addr;
            $('#address1').attr("readonly", true);

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("address2").focus();
        }
    }).open();
}