// 입력 Form의 빈 값 여부를 판단하고, 이상이 없을 경우 회원정보 수정을 담당하는 함수를 호출
function checkForm() {
    if ($("#password").val() == '' || $("#password").val() == null) {
        alert("비밀번호를 입력하세요.");
        $("#password").focus();
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
    if ($("#address").val() == '' || $("#address").val() == null) {
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

    fn_EditMember();
}

// 회원정보 수정 처리를 담당하는 함수.
function fn_EditMember() {
    Point_Service();
    alert('회원정보 수정이 완료되었습니다.')
    document.EditMember.action = '/member/processUpdateMember.do';
    document.EditMember.birth.value = document.EditMember.birthyy.value + document.EditMember.birthmm.value + document.EditMember.birthdd.value
    document.EditMember.mail.value = document.EditMember.mail1.value + "@" + document.EditMember.mail2.value
    document.EditMember.submit();
}

// 이메일/SMS 수신 동의 여부를 판단하기 위해 입력 Form의 값은 Controller에 전달하는 함수
function Point_Service(){
    var id = $("#id").val();
    var name = $("#name").val();
    var phone = $("#phone").val();
    var email_agree_value = $('input[name="email_agree"]:checked').val();
    var sms_agree_value = $('input[name="sms_agree"]:checked').val();
    var data = {
        id : id,
        name : name,
        phone : phone,
        email_agree : email_agree_value,
        sms_agree : sms_agree_value
    }

    $.ajax({
        url: '/Point_Service',
        type: 'POST',
        data: data,
    });
}

// 회원정보 삭제를 위한 Parameter 전달 함수
function DeleteMember() {
    var id = $("#id").val();
    if(confirm("회원탈퇴를 진행하시려면 확인버튼을 눌러주세요.") == true){
        alert('회원탈퇴가 완료되었습니다.');
        location.href = "/member/deleteMember.do?id="+id;
    }
}
