// 로그인 시, 아이디와 비밀번호의 일치여부를 판단하여 알려주는 함수
function loginCheck(){
    var id = $("#id").val();
    var password = $("#password").val();
    var data = {
        id : id,
        password : password
    }

    $.ajax({
        url: '/Login_Check',
        type: 'POST',
        data: data,

        success: function(result){
            if(result != 'fail'){
                alert('로그인 처리 되었습니다.')
                location.href="/";
            } else
                alert('아이디 또는 비밀번호를 다시 확인해주세요.')
        }
    });
}